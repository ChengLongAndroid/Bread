package com.funcy.g01.base.bo.email;

import com.funcy.g01.base.proto.bo.GlobalBoProtoBuffer.SysEmailStatusProto;



public class SysEmailStatus {

    private Integer mailId;

    private EmailState state;
    
    public SysEmailStatus() {
	}

    public SysEmailStatus(int emailId, EmailState state) {
    	this.mailId = emailId;
    	this.state = state;
	}
    
    public SysEmailStatus(SysEmailStatusProto proto){
		this.mailId = proto.getMailId();
		this.state = EmailState.valueOf(proto.getState());
    }
    
    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

	public EmailState getState() {
		return state;
	}

	public void setState(EmailState state) {
		this.state = state;
	}

	public SysEmailStatusProto copyTo() {
		SysEmailStatusProto.Builder builder = SysEmailStatusProto.newBuilder();
    	builder.setMailId(mailId);
    	builder.setState(this.state.getCode());
    	return builder.build();
    }
}