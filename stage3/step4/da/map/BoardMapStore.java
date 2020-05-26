package javastory.club.stage3.step4.da.map;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javastory.club.stage3.step1.entity.board.SocialBoard;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.store.BoardStore;
import javastory.club.stage3.step4.util.MemberDuplicationException;

public class BoardMapStore implements BoardStore {
	//
	private Map<String,SocialBoard> boardMap; 
	
	public BoardMapStore() {
		//  
		this.boardMap = MemoryMap.getInstance().getBoardMap(); 
	}
	
	@Override
	public String create(SocialBoard board) {
		// 
//		if (boardMap.get(board.getId()) != null) {
//			throw new MemberDuplicationException("Member already exists with email: " + board.getId()); 
//		}
//		
//		boardMap.put(board.getId(), board);
		
		Optional.ofNullable(boardMap.get(board.getId())).ifPresent(targetBoard->{throw new MemberDuplicationException("Member already exists with email: " + board.getId());});
		
		boardMap.put(board.getId(), board);
		return board.getId();
	}

	@Override
	public SocialBoard retrieve(String boardId) {
		// 
		return boardMap.get(boardId); 
	}
	
	@Override
	public List<SocialBoard> retrieveByName(String name) {
		//
//		List<SocialBoard> boards = new ArrayList<>(); 
//		Iterator<SocialBoard> boardIter = boardMap.values().iterator(); 
//		while(boardIter.hasNext()) {
//			SocialBoard board = boardIter.next(); 
//			if (board.getName().equals(name)) {
//				boards.add(board); 
//			}
//		}
		
		return boardMap.values().stream().filter(board->board.getName().equals(name)).collect(Collectors.toList()); 
	}

	@Override
	public void update(SocialBoard board) {
		// 
//		if (boardMap.get(board.getId()) == null) {
//			throw new NoSuchElementException("No such a element: " + board.getId()); 
//		}
		
//		Optional.ofNullable(boardMap.get(board.getId())).orElseThrow(()->new NoSuchElementException("No such a element: " + board.getId()));
		
		boardMap.put(board.getId(), board); 
	}

	@Override
	public void delete(String boardId) {
		// 
		boardMap.remove(boardId);
	}

	@Override
	public boolean exists(String boardId) {
		//
//		if(boardMap.get(baordId) != null) {
//			return true; 
//		};
//		
//		return false;
		
		return Optional.ofNullable(boardMap.get(boardId)).isPresent();
	}
}
