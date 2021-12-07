package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sbs.example.easytextboard.dto.Member;

public class MemberController extends Controller {
	private List<Member> members;
	private int lastMemberId;

	public MemberController() {
		lastMemberId = 0;
		members = new ArrayList<>();
	}

	public void run(Scanner scan, String command) {
		if (command.equals("member join")) {
			System.out.println("== 회원가입 ==");

			String loginId = "";
			String loginPw;
			String name;

			int loginIdMaxCount = 3;
			int loginIdCount = 0;
			boolean LoginIdIsValid = false;

			while (true) {
				if (loginIdMaxCount <= loginIdCount) {
					System.out.println("회원가입을 취소합니다");
					break;
				}
				System.out.print("로그인 아이디 :");
				loginId = scan.nextLine().trim();
				if (loginId.length() == 0) {
					loginIdCount++;
					continue;
				}
				else if (isJoinAvailable(loginId) == false) {
					loginIdCount++;
					System.out.println("%s은(는) 이미 사용중인 로그인 아이디 입니다.");
					continue;
				}

				LoginIdIsValid = true;
				break;
			}
			if (LoginIdIsValid == false) {
				return;
			}
			System.out.print("로그인 패스워드 :");
			loginPw = scan.nextLine();
			System.out.print("이름 :");
			name = scan.nextLine();

			int id = join(loginId, loginPw, name);

			System.out.printf("%d번 회원이 생성되었습니다.\n", id);
		}
	}

	private boolean isJoinAvailable(String loginId) {
		
		return false;
	}

	// 회원가입
	private int join(String loginId, String loginPw, String name) {
		Member member = new Member();
		member.id = lastMemberId + 1;
		member.loginId = loginId;
		member.loginPw = loginPw;
		member.name = name;

		members.add(member);

		lastMemberId = member.id;

		return member.id;
	}

}
