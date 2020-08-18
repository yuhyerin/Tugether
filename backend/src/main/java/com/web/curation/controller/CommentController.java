package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.comment.Comment;
import com.web.curation.dto.comment.FrontComment;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.comment.CommentService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/tugether")
public class CommentController {
   
   @Autowired
   private JwtService jwtService;
   
   @Autowired
   private CommentService commentService;
   
   @GetMapping("/mainfeed/comment")
   @ApiOperation(value = "댓글페이지")
   public ResponseEntity<Map<String,Object>> commentPage(@RequestParam("article_id")Integer article_id,HttpServletRequest request) {
      Map<String, Object> resultMap = new HashMap<String, Object>();
      String token = request.getHeader("jwt-auth-token");   //토큰 가져와서
      Jws<Claims> claims = jwtService.getDecodeToken(token);   //복호화해서
      Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
      String email = Userinfo.get("email").toString();
      System.out.println("article_id : "+article_id);
      FrontArticle article = commentService.findArticle(email, article_id);
      System.out.println(article.toString());
      resultMap.put("article", article);
      List<FrontComment> comments = commentService.findComments(article_id);
      System.out.println(comments.toString());
      resultMap.put("comments", comments);
      System.out.println("result : "+comments.toString());
      return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
   }
   
   @PostMapping("/mainfeed/comment")
   @ApiOperation(value="댓글작성")
   public ResponseEntity<Map<String,Object>> writeComment(@RequestBody Comment comment, HttpServletRequest request){
       int article_id = comment.getArticle_id();
       String content = comment.getContent();
       
       Map<String, Object> resultMap = new HashMap<>();
       System.out.println(request.getHeader("jwt-auth-token"));
      Jws<Claims> claims = jwtService.getDecodeToken(request.getHeader("jwt-auth-token"));
      Map<String, Object> Userinfo = new HashMap<String, Object>();
      Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
      String email = Userinfo.get("email").toString();
      Comment c = Comment.builder().article_id(article_id).content(content).email(email).build();
      System.out.println(c.toString());
       commentService.saveComment(c);
       resultMap.put("status", true);
       resultMap.put("data", "success");
       return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.ACCEPTED);
    }
   
   @GetMapping("/mainfeed/deleteComment")
   @ApiOperation(value="댓글삭제")
   public ResponseEntity<Map<String,Object>> deleteComment(@RequestParam("comment_id")Integer comment_id, HttpServletRequest request) {
	   Map<String, Object> resultMap = new HashMap<String, Object>();
	   // comment_id 삭제 -> article_tb에서 article_id comment_cnt-1
	   
//	   commentService.deleteComment(Integer.parseInt(request.getHeader("comment_id")));
	   commentService.deleteComment(comment_id);
	   return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
   }
}