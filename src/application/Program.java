package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = new Seller();
		Department dep = new Department();
		List<Seller> list = new ArrayList<>();

		System.out.println();
		System.out.println("=== TEST 1: seller findById ===");
		System.out.print("id: ");
		int id = sc.nextInt();
		seller = sellerDao.findById(id);
		System.out.println(seller);

		System.out.println();
		System.out.println("=== TEST 2: seller findByDepartment ===");
		System.out.print("id: ");
		id = sc.nextInt();
		dep = new Department(id, null);
		list = sellerDao.findByDepartment(dep);
		list.forEach(System.out::println);

		System.out.println();
		System.out.println("=== TEST 3: seller findaAll ===");
		list = sellerDao.findAll();
		list.forEach(System.out::println);

		sc.close();
	}
}