package com.example.study.dto._event;


import lombok.Getter;

@Getter
public enum ResultCode {
	ERR_0000("0000","成功執行"),
	ERR_2001("2001","參數錯誤"),
	ERR_2002("2002","帳號密碼錯誤"),
	ERR_2003("2003","尚未完成token驗證"),
	// 8開頭是驗證回傳結果
	ERR_8001("8001","重複命名"),
	ERR_8002("8002","上傳檔案與原檔不符，請重新上傳"),
	ERR_9001("9001","出現不可預期的錯誤，請聯繫工作人員。")
	;
	
	
	private final String code;
	private final String desc;

	ResultCode(String pCode,String pDesc){
		this.code = pCode;
		this.desc = pDesc;
	}

}
