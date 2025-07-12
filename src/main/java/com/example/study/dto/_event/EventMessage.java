package com.example.study.dto._event;

import lombok.Data;

/**
 * 事件訊息.
 */
@Data
public class EventMessage<OBJECT> {
	private String eventCode;
	private String eventDescription;
	private OBJECT eventMessage;
}
