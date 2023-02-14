package haule.raelfarm.singleton;

import haule.raelfarm.service.BoardService;

public class CommentInfo {
	private static CommentInfo instance;
	private int commentno;
	
	private CommentInfo(BoardService boardService) {

		this.commentno = boardService.SelectBoardCommentMAXCommentNo();
	}
	
	public static CommentInfo getInstance(BoardService boardService) {
		
		if (instance == null) {
			instance = new CommentInfo(boardService);
		}
		return instance;
	}
	
	public int getCommentNumber() {
		this.commentno += 1;
		return commentno;
	}
}
