package org.home.proquiz.controllers; 

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.home.proquiz.entities.Answer;
import org.home.proquiz.entities.Comment;
import org.home.proquiz.entities.Interview;
import org.home.proquiz.entities.Question;
import org.home.proquiz.entities.User;
import org.home.proquiz.entities.UserAnswer;
import org.home.proquiz.json.CommentRequestJson;
import org.home.proquiz.json.ErrorMessageResponseJson;
import org.home.proquiz.json.InterviewRequestJson;
import org.home.proquiz.json.ResponseJson;
import org.home.proquiz.json.UserAnswerResponseJson;
import org.home.proquiz.services.CommentService;
import org.home.proquiz.services.InterviewService;
import org.home.proquiz.services.SearchService;
import org.home.proquiz.services.UserAnswerService;
import org.home.proquiz.services.UserService;
import org.home.proquiz.util.CommonUtils;
import org.home.proquiz.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@RequestMapping("/api/interview")
public class InterviewAPIController {
	@Autowired
	private UserService uSer;
	@Autowired
	private InterviewService iSer;
	@Autowired
	private UserAnswerService uaSer;
	@Autowired
	private CommentService cSer;
	@Autowired
	private SearchService sSer; 
	
	@GetMapping(value="/search", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> search(@RequestParam("input-search")String toSearch, 
									@RequestParam("mode")String mode) {
		
		switch(mode) {
			case "name": 
				return ResponseEntity.ok(sSer.searchByUserName(toSearch));
			case "title": 
				return ResponseEntity.ok(sSer.searchByTitle(toSearch));
			default:
				return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping(value="/save", 
			     produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseJson<?>> addInterview(@RequestBody @Valid 
														InterviewRequestJson req, 
														BindingResult errRes, 
														@SessionAttribute("user")User user) {
		
		if(errRes.hasErrors()) {
			StringBuilder out = new StringBuilder();
			errRes.getAllErrors().forEach(e -> {
				out.append(e.getDefaultMessage());
				out.append("\n");
				out.append("<br>");
			});
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of(out.toString()));
		}
		
		if(!req.getInterview().getAuthor().getId().equals(user.getId())) {
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of("Error: user id is wrong"));
		}
			
		
		req.getInterview().setDate(LocalDateTime.now(ZoneId.of("Europe/Kiev")));
		iSer.save(req.getInterview(), req.getCategoryId());
		
		return ResponseEntity.ok().header("Content-type", "application/json; charset=utf-8").build();
	}
	
	@PostMapping(value="/save/answer", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> saveUserAnswer(@RequestBody @Valid UserAnswer req, 
											BindingResult errRes, 
											@SessionAttribute("user")User user) {
		if(errRes.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(ObjectError o: errRes.getAllErrors()) {
				sb.append(o.getDefaultMessage());
				sb.append('\n');
			}
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of(sb.toString()));
		}
		
		if(!user.getId().equals(req.getUserId())) 
					return ResponseEntity
							.badRequest()
							.body(ErrorMessageResponseJson.of("Error: user id is wrong"));
		
		if(uSer.exist(req.getUserId()) && iSer.exist(req.getInterviewId())) {
			if (iSer.countCorrectAnswers(req.getQuestionId()) == 0 && uaSer.countAnswersOfUser(req) == 0) {
				uaSer.saveUserAnswer(req);
				boolean isCorrect = true;
				boolean canVote   = true;
				return ResponseEntity.ok(UserAnswerResponseJson.of(canVote, isCorrect, req));
			} else if(iSer.countCorrectAnswers(req.getQuestionId()) > uaSer.countAnswersOfUser(req)) {
				boolean isCorrect = iSer.isCorrect(req.getAnswerId());
				boolean canVote   = iSer.countCorrectAnswers(req.getQuestionId()) > uaSer.countAnswersOfUser(req);
				uaSer.saveUserAnswer(req);
				return ResponseEntity.ok(UserAnswerResponseJson.of(canVote, isCorrect, req));
			} else {
				boolean isCorrect = true;
				boolean canVote   = false;
				return ResponseEntity.ok(UserAnswerResponseJson.of(canVote, isCorrect, req));
			}
		}
		
		return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of("Error save answer"));
	}
	
	@PutMapping(value="/edit", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<ResponseJson<?>> editInterview(@RequestBody @Valid Interview toEdit,
														 BindingResult errRes, 
														 @SessionAttribute("user")User user) {
		if(errRes.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(ObjectError o: errRes.getAllErrors()) {
				sb.append(o.getDefaultMessage());
				sb.append('\n');
			}
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of(sb.toString()));
		}
		
		if(!user.getId().equals(toEdit.getAuthor().getId()))
				return ResponseEntity
						.badRequest()
						.body(ErrorMessageResponseJson.of("Error: user id is wrong"));
		
		toEdit.setAuthor(user);
		iSer.edit(toEdit);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseJson<?>> deleteById(@PathVariable("id") Long id,
										     	      @SessionAttribute("user")User user) {
		if(id < 0) 
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of("Error: id less then zero!"));

		if(!iSer.isAuthor(user.getId(), id))
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of("Error: the user is not a author!"));
		
		iSer.delete(id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value="/delete/answers/{interview_id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> resetUserAnswer(@SessionAttribute("user")User user, 
			                                 @PathVariable("interview_id") long interviewId) {
		
		if(interviewId < 0) 
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of("Interview id is less then zero"));
		
		uaSer.resetUserAnswers(interviewId, user.getId());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<?> getById(@PathVariable("id") Long id) {
		if(id < 0) 
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of("Error: Id is less then zero"));
		if(!iSer.exist(id)) 
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of("No interview by id: " + id));
		return ResponseEntity.ok().body(iSer.getById(id));
	}
	
	@GetMapping(value="/answer/{interview_id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getAnswerById(@SessionAttribute("user") User user, 
														 @PathVariable("interview_id") long interviewId) {
		
		List<UserAnswerResponseJson> out = new ArrayList<>();
		List<UserAnswer> res = uaSer.getUserAnser(interviewId, user.getId());
		boolean correct = false;
		for(UserAnswer ua: res) {
			if(iSer.countCorrectAnswers(ua.getQuestionId()) == 0) {
				out.add(UserAnswerResponseJson.of(false, true, ua));
				continue;
			}
				
			correct = iSer.isCorrect(ua.getAnswerId());
			out.add(UserAnswerResponseJson.of(false, correct, ua));
		}
		
		return ResponseEntity.ok().body(out);
	}
	
	@GetMapping(value="/answer/check/{answer_id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> checkAnswer(@PathVariable("answer_id") long answerId) {
		return ResponseEntity.ok().body(iSer.isCorrect(answerId));
	}
	
	@GetMapping(value="/page/{category_id}/{page_number}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getNextPage(@PathVariable("page_number") Integer page, 
			                             @PathVariable("category_id") Long categoryId) {
		
		if(page < 0 || categoryId < 0) {
			return ResponseEntity
					.badRequest()
						.body(ErrorMessageResponseJson
								.of("Error: page or catiogry id is less then zero"));
		}
		
		List<Interview> list =  iSer.getPageByCategory(categoryId, page, 
									 PageInfo.PAGE.getWebInterviewPageSize()).getContent();
		return ResponseEntity.ok(CommonUtils.processList(list));
	}
	
	@GetMapping(value="/page/statistics/{page_number}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getStatPageByUser(@PathVariable("page_number") Integer page,
											   @SessionAttribute("user")User user) {
		
		if(page < 0) {
			return ResponseEntity
					.badRequest()
						.body(ErrorMessageResponseJson
								.of("Error: page is less then zero"));
		}
		List<Interview> list = iSer.getByUserId(user.getId(), page, PageInfo.PAGE.getStatisticsPageSize());
		
		list = CommonUtils.processUser(list);
		for(Interview i: list) {
			Short count = iSer.countVotes(i.getId());
			i.setCount(count != null ? count : 0);
			for(Question q: i.getQuestions()) {
				for(Answer a: q.getAnswers()) {
					a.setCount(uaSer.countAnswers(i.getId(), q.getId(), a.getId()));
				}
			}
		}
		
		return ResponseEntity.ok(list);
	}
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Comment section ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	@PostMapping(value="/comment/save", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> saveComment(@RequestBody @Valid CommentRequestJson req,
			                             BindingResult errRes,
			                             @SessionAttribute("user")User user) {
		
		if(errRes.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for(ObjectError o: errRes.getAllErrors()) {
				sb.append(o.getDefaultMessage());
				sb.append('\n');
			}
			return ResponseEntity.badRequest().body(ErrorMessageResponseJson.of(sb.toString()));
		}
		
		if(!iSer.exist(req.getInterviewId())) {
			return ResponseEntity
					.badRequest()
						.body(ErrorMessageResponseJson
								.of("Error: interview id - " + req.getInterviewId() + ", not exist"));
		}
			
			
		req.getComment().setDate(LocalDateTime.now(ZoneId.of("Europe/Kiev")));
		if(iSer.isComment(req.getInterviewId())) {
			req.getComment().setAuthor(null);
		} else {
			req.getComment().setAuthor(user);
		}

		cSer.save(req.getComment(), req.getInterviewId());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(value="/comment/{interview_id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getComments(@PathVariable("interview_id") Long interviewId){ 
		if(interviewId < 0) {
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of("Error: Interview id is less then zero"));
		}
		return ResponseEntity.ok(cSer.getByInterviewId(interviewId)); 
	}
	
	@DeleteMapping(value="/comment/{interview_id}/{comment_id}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteComment(@PathVariable("interview_id") Long interviewId, 
										   @PathVariable("comment_id")  Long commentId){
		
		if(interviewId < 0 || commentId < 0) {
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of("Error: Interview id or Comment id is less then zero"));
		}
			
		cSer.delete(commentId);
		return ResponseEntity.ok().build(); 
	}
	
	@DeleteMapping(value="/comment/{interview_id}/", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> deleteAllComments(@PathVariable("interview_id") Long interviewId){
		
		if(interviewId < 0) {
			return ResponseEntity
					.badRequest()
					.body(ErrorMessageResponseJson.of("Error: Interview id is less then zero"));
		}

		cSer.deleteAll(interviewId);
		return ResponseEntity.ok().build(); 
	}
	
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Comment section ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
}
