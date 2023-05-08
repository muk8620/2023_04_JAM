package com.KoreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.KoreaIT.JAM.controller.ArticleController;
import com.KoreaIT.JAM.controller.MemberController;

public class App {

	public void run() {
		System.out.println("== 프로그램 시작 ==");

		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			MemberController memberController = new MemberController(conn, sc);
			ArticleController articleController = new ArticleController(conn, sc);
			

			while (true) {
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
	
				if (cmd.equals("exit")) {
					System.out.println("== 프로그램 끝 ==");
					break;
				}
				
				if (cmd.equals("member join")) {
					memberController.doJoin();
				
				} else if (cmd.equals("article write")) {
					articleController.doWrite();
	
				} else if (cmd.equals("article list")) {
					articleController.showList();
					
				} else if (cmd.startsWith("article detail ")) {
					articleController.showDetail(cmd);
					
				} else if (cmd.startsWith("article modify ")) {
					articleController.doModify(cmd);

				} else if (cmd.startsWith("article delete ")) {
					articleController.doDelete(cmd);

				} else {
					System.out.printf("%s는 존재하지 않는 명령어입니다.\n", cmd);
				}
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러: " + e);
		} finally {
//				try {
//					if (pstmt != null && !pstmt.isClosed()) {
//						pstmt.close();
//					}
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}	
		sc.close();
	}
}
