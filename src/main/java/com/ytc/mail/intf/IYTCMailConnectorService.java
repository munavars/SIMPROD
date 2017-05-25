package com.ytc.mail.intf;

import com.ytc.common.model.EmailDetails;

public interface IYTCMailConnectorService {
	void sendEmail(EmailDetails emailDetails);
}
