package com.sbs.test.mysqltextboard.service;

import com.sbs.test.mysqltextboard.container.Container;
import com.sbs.test.mysqltextboard.dao.MemberDao;
import com.sbs.test.mysqltextboard.dto.Member;

public class MemberService {

	private MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public int join(String id, String pw, String name) {
		return memberDao.join(id, pw, name);
	}

	public Member login(String id, String pw) {
		return memberDao.login(id, pw);
	}

	public void logout() {
		memberDao.logout();
	}
}
