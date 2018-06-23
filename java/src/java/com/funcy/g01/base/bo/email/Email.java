package com.funcy.g01.base.bo.email;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.EmailProto;


public class Email {

	public static final int SYSTEM_EMAIL_CONTINUE_DAYS = 15;

	private int mailId;

    private Long accepterId;

    private String accepterName;

    private String title;

    private String content;

    private Long senderId;
    
    private String senderName;

    private String rewards;

    private Long createTime;

    private EmailType type;
    
    private EmailState state;

	@SuppressWarnings("unused")
	private Email() {
    }
    
    public Email(int mailId, String title, String content, long senderId, String senderName, EmailType emailType) {
    	this.mailId = mailId;
//    	this.accepterId = accepterId;
//    	this.accepterName = accepterName;
		this.title = title;
		this.content = content;
		this.senderId = senderId;
		this.senderName = senderName;
		this.createTime = System.currentTimeMillis();
		this.accepterName = "";
		this.rewards = "";
		this.type = emailType;
		this.state = EmailState.NEW;
	}
    
    public Email(EmailProto emailProto){
		this.mailId = emailProto.getMailId();
		this.accepterId = emailProto.getAccepterId();
		this.accepterName = emailProto.getSenderName();
    	this.title = emailProto.getTitle();
		this.content = emailProto.getContent();
		this.senderId = emailProto.getSenderId();
		this.senderName = emailProto.getSenderName();
		this.createTime = emailProto.getCreateTime();
		this.rewards = emailProto.getRewards();
		this.type = EmailType.valueOf(emailProto.getType());
		this.state = EmailState.valueOf(emailProto.getState());
    }

    public String getAccepterName() {
        return accepterName;
    }

    public void setAccepterName(String accepterName) {
        this.accepterName = accepterName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getRewards() {
        return rewards;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getAccepterId() {
        return accepterId;
    }

    public void setAccepterId(Long accepterId) {
        this.accepterId = accepterId;
    }
    
    public Boolean isSelfEmail(long roleId) {
    	return accepterId == roleId;
    }

	public EmailType getType() {
		return type;
	}

	public void setType(EmailType type) {
		this.type = type;
	}

	public EmailState getState() {
		return state;
	}

	public void setState(EmailState state) {
		this.state = state;
	}

	public EmailProto copyTo() {
		EmailProto.Builder builder = EmailProto.newBuilder();
    	builder.setMailId(mailId);
    	builder.setAccepterId(this.accepterId);
    	builder.setAccepterName(this.accepterName);
    	builder.setContent(this.content);
    	builder.setTitle(this.title);
    	builder.setCreateTime(this.createTime);
    	builder.setSenderId(this.senderId);
    	builder.setSenderName(this.senderName);
    	builder.setRewards(this.rewards);
    	builder.setType(this.type.getCode());
    	builder.setState(this.state.getCode());
    	return builder.build();
    }

	public int getMailId() {
		return mailId;
	}

	public void setMailId(int mailId) {
		this.mailId = mailId;
	}
}