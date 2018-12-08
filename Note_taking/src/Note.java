import java.util.Date;
import java.util.Optional;

public class Note {
	private int noteId;
	private String content;
	private String title;
	private Date createdDate;
	private Date alertDate;
	int ownerId;
	public Note() {
		
	}
	public Note(String content, String title, Date createdDate, int ownerId) {
		this.content = content;
		this.title = title;
		this.createdDate = createdDate;
		this.ownerId = ownerId;
	}
	
	public Note(String content, String title, Date createdDate, int ownerId, Date alertedDate) {
		this.content = content;
		this.title = title;
		this.createdDate = createdDate;
		this.ownerId = ownerId;
		this.alertDate = alertedDate;
	}
	public int getNoteId() {
		return noteId;
	}
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getAlertDate() {
		return alertDate;
	}
	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	
	
}
