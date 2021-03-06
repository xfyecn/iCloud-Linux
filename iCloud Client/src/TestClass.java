
import icloud.CloudAuthenticator;
import icloud.CloudSessionManager;
import icloud.Credentials;
import icloud.Request;
import icloud.event.CloudEvent;
import icloud.request.event.RequestEvent;
import icloud.request.event.RequestMadeEvent;
import icloud.request.event.RequestRecievedEvent;
import icloud.request.handler.RequestEventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import apps.note.event.NoteEvent;
import apps.note.handler.NoteEventHandler;
import apps.note.request.NoteCreateRequest;
import apps.note.request.NoteDeleteRequest;
import apps.note.request.NoteFetchRequest;
import apps.note.request.NoteListRequest;
import apps.note.request.NoteRequest;
import apps.note.request.NoteUpdateRequest;


public class TestClass implements RequestEventHandler, NoteEventHandler {

	private ArrayList<Request> requestList = new ArrayList<Request>();

	public static void main(String[] args) {
		new TestClass().start(args);
	}

	private void start(String[] args) {
		Credentials authKeys = new Credentials(args[0], args[1], false, Credentials.AuthenticationTypes.PASSWORD);
		System.out.println("Logging In...");
		UUID sessionKey = CloudSessionManager.getInstance().createSession(authKeys);
		CloudSessionManager.getInstance().authenticateSession(sessionKey);
		System.out.println("Logged In");
		
		System.out.println("Generating Requests...");
		requestList.addAll(generateNoteRequests());
		
		System.out.println("Executing Requests...");
		for (Request req : requestList){
			if(getUserBoolean("Do you wish to execute (y/n): " + req.getClass().getName())){
				CloudSessionManager.getInstance().executeRequest(sessionKey, req);
			} else {
				System.out.println("Skipping");
			}
		}
		
		System.out.println("Logging Out...");
		CloudSessionManager.getInstance().deAuthenticateSession(sessionKey);
		System.out.println("Logged Out");
	}


	private List<NoteRequest> generateNoteRequests() {
		ArrayList<NoteRequest> list = new ArrayList<NoteRequest>();
		
		NoteCreateRequest noteCReq = new NoteCreateRequest(/*The Note or Notes to be created*/ /*The session Key*/);
		noteCReq.addEventHandler(this);
		list.add(noteCReq);
		
		NoteUpdateRequest noteUReq = new NoteUpdateRequest(/*The Note or Notes to be updated*/);
		noteUReq.addEventHandler(new NoteEventHandler(){

			@Override
			public void onEvent(CloudEvent evt) {
				// TODO Auto-generated method stub
				System.out.println("The Events!");
				
			}

			@Override
			public void onNoteEvent(NoteEvent evt) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNoteCreateEvent(NoteEvent evt) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNoteDeleteEvent(NoteEvent evt) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNoteUpdateEvent(NoteEvent evt) {
				// TODO Auto-generated method stub
				
			}
		});
		list.add(noteUReq);
		
		NoteDeleteRequest noteDReq = new NoteDeleteRequest(/*The Note or Notes to be deleted*/);
		list.add(noteDReq);
		
		NoteFetchRequest noteFReq = new NoteFetchRequest(/*The Note or Notes to be fetched*/);
		list.add(noteFReq);
		
		NoteListRequest noteLReq = new NoteListRequest();
		list.add(noteLReq);
		return list;
	}

	@SuppressWarnings("resource")
	private boolean getUserBoolean(String string) {
		System.out.println(string);
		return (new Scanner(System.in).nextLine().equalsIgnoreCase("y") ? true : false );
	}


	@Override
	public void onEvent(CloudEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestEvent(RequestEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestMadeEvent(RequestMadeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestReceivedEvent(RequestRecievedEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNoteEvent(NoteEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNoteCreateEvent(NoteEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNoteDeleteEvent(NoteEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNoteUpdateEvent(NoteEvent evt) {
		// TODO Auto-generated method stub
		
	}
	
}
