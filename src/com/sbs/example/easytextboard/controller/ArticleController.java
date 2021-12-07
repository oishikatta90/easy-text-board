package com.sbs.example.easytextboard.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sbs.example.easytextboard.controller.MemberController;
import com.sbs.example.easytextboard.dto.Article;
import com.sbs.example.easytextboard.dto.Member;

public class ArticleController {
	private List<Article> articles;


	private int lastArticleId;

	public ArticleController() {
		lastArticleId = 0;
		articles = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			add("제목" + (i + 1), "내용" + (i + 1));
		}
	}

	private Article getArticle(int id) {
		int index = getIndexById(id);

		if (index == -1) {
			return null;
		}
		return articles.get(index);
	}

	private int add(String title, String body) {
		Article article = new Article();
		article.id = lastArticleId + 1;
		article.title = title;
		article.body = body;

		articles.add(article);

		lastArticleId = article.id;

		return article.id;
	}

	private void remove(int id) {
		int index = getIndexById(id);
		if (index == -1) {
			return;
		}
		articles.remove(index);
	}

	private int getIndexById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			if (articles.get(i).id == id) {
				return i;
			}
		}
		return -1;
	}

	private void modify(int inputedId, String title, String body) {
		Article article = getArticle(inputedId);
		article.title = title;
		article.body = body;
	}
	


	// 가장 상위층 시작
	public void run(Scanner scan, String command) {


			if (command.equals("article add")) {
				System.out.println("==게시물 등록==");

				String title;
				String body;

				System.out.print("제목 :");
				title = scan.nextLine();
				System.out.print("내용 :");
				body = scan.nextLine();

				int id = add(title, body);

				System.out.printf("%d번 게시무링 생성되었습니다.\n", id);
			} else if (command.equals("article search")) {
				System.out.println("==게시물 검색==");

				String[] commandBits = command.split(" ");

				String searchKeyword = commandBits[2];

				int page = 1;

				if (commandBits.length >= 4) {
					page = Integer.parseInt(commandBits[3]);
				}

				if (page <= 1) {
					page = 1;
				}
				System.out.println("==게시물 검색==");

				List<Article> searchResultArticles = new ArrayList<>();

				// 검색된 결과의 수를 먼저 구하기
				for (Article article1 : articles) {
					if (article1.title.contains(searchKeyword)) {
						searchResultArticles.add(article1);
					}
				}
					if (searchResultArticles.size() == 0) {
						System.out.println("검색결과가 존재하지 않습니다.");
						return;
					}
					System.out.println("번호 / 제목");
					
					int itemsInAPage = 0;
					int startPos = searchResultArticles.size() - 1;
					startPos -= (page - 1) * itemsInAPage;
					int endPos = startPos - (itemsInAPage - 1);
					
					if (endPos < 0) {
						endPos = 0;
					}
					if (startPos < 0) {
						System.out.printf("%페이지는 존재하지 않습니다.\n", page);
						return;
					}
					
					for(int i = startPos; i>= endPos; i--) {
						Article article = searchResultArticles.get(i);
						
						System.out.printf("%d / %s\n", article.id, article.title);
					}
				

			} else if (command.startsWith("article list ")) {
				int page = Integer.parseInt(command.split(" ")[2]);

				if (page <= 1) {
					page = 1;
				}
				System.out.println("==게시물 리스트==");

				if (articles.size() == 0) {
					System.out.println("게시물이 존재하지 않습니다.");
					return;
				}
				System.out.println("번호 / 제목");

				int itemsInAPage = 10;
				int startPos = articles.size() - 1;
				startPos -= (page - 1) * itemsInAPage;
				int endPos = startPos - (itemsInAPage - 1);

				if (endPos < 0) {
					endPos = 0;
				}

				if (startPos < 0) {
					System.out.printf("%d 페이지는 존재하지 않습니다.", page);
					return;
				}

				for (int i = startPos; i >= endPos; i--) {
					Article article = articles.get(i);

					System.out.printf("%d / %s\n", article.id, article.title);
				}
			} else if (command.startsWith("article detail")) {
				int inputedId = 0;
				try {
					inputedId = Integer.parseInt(command.split(" ")[2]);

				} catch (NumberFormatException e) {
					System.out.println("게시물 번호는 정수로만 입력해주세요!");
					return;
				}
				System.out.println("==게시물 상세==");

				Article article = getArticle(inputedId);

				if (article == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", inputedId);
					return;
				}
				System.out.printf("번호 : %d 제목: %s 내용: %s\n", article.id, article.title, article.body);

			} else if (command.startsWith("article delete")) {
				int inputedId = Integer.parseInt(command.split(" ")[2]);
				System.out.println("==게시물 삭제==");

				Article article = getArticle(inputedId);

				if (article == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", inputedId);
					return;
				}

				remove(inputedId);

				System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputedId);
			} else if (command.startsWith("article modify")) {
				int inputedId = Integer.parseInt(command.split(" ")[2]);
				System.out.println("==게시물 수정==");

				Article article = getArticle(inputedId);

				if (article == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", inputedId);
					return;
				}
				System.out.printf("번호 : %d\n", article.id);
				System.out.print("제목: ");
				String title = scan.nextLine();
				System.out.print("내용: ");
				String body = scan.nextLine();

				modify(inputedId, title, body);
				System.out.printf("%d번 게시물이 수정되었습니다.\n", inputedId);
			}
		}
	}