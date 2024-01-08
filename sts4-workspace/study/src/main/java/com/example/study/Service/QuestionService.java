package com.example.study.Service;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.study.DataNotFoundException;
import com.example.study.Entity.Answer;
import com.example.study.Entity.Question;
import com.example.study.Entity.SiteUser;
import com.example.study.Repository.QuestionRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;

	
	public List<Question> getList(){
		return this.questionRepository.findAll();
	}
	
	public Question getQuestion(Long id) {
		Optional<Question> question = this.questionRepository.findById(id);
		
		if(question.isPresent()) { // 해당 데이터가 존재하는지 여부 검사
			return question.get();
		}else {
			throw new DataNotFoundException("question not found");
		}
	}
	// 생성
	public void create(String subject, String content, SiteUser user) {
		Question q = new Question();
		
		q.setSubject(subject);
		q.setContent(content);
		q.setCreateDate(LocalDateTime.now());
		q.setAuthor(user);
		this.questionRepository.save(q);
	}
	// 수정
	public void modify(Question question, String subject, String content) {
		
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	// 삭제
	public void delete(Question question) {
		this.questionRepository.delete(question);
	}
	
	// 페이징 처리 메소드
	public Page<Question> getPageList(int page, String keyword) {
		
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate")); // 작성일시 외에 추가로 정렬 조건이 필요한 경우에는 sorts 리스트에 추가하면 됨
		
		Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts)); // 조회 할 페이지의 번호, 한 페이지에 보여줄 게시물의 갯수, 역순 조회
		Specification<Question> spec = search(keyword);
		
		return this.questionRepository.findAll(spec,pageable);
	}
	
	public long totalCount() {
		long count = this.questionRepository.count();
		
		return count;
	}
	
	private Specification<Question> search(String keyword) {
		return new Specification<>() {
			
			@Override
			public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				query.distinct(true); // 중복제거
				Join<Question, SiteUser> u1 = root.join("author", JoinType.LEFT);
				Join<Question, Answer> a = root.join("answersList", JoinType.LEFT);
				Join<Answer, SiteUser> u2 = root.join("author", JoinType.LEFT);
				
				return cb.or(cb.like(root.get("subject"),"%" + keyword + "%"), // 제목
						cb.like(root.get("content"), "%" + keyword + "%"), // 내용
						cb.like(u1.get("username"), "%" + keyword + "%"), // 질문자 이름
						cb.like(a.get("content"), "%" + keyword + "%"), // 답변자 내용
						cb.like(u2.get("username"), "%" + keyword + "%")); // 답변자 이름
			}
		};
	}
}
