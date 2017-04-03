/**
 * 
 */
package com.ytc.service;


import com.ytc.common.model.User;

/**
 * @author 164919
 *
 */
public interface ISecurityService {
	User getUser(String userName);

	User authenticateUIUser(String userName);
}
