package application;

import java.util.ArrayList;
import java.util.Date;
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
		
		System.out.println();
		System.out.println("=== TEST 4: seller insert ===");
		Seller newSeller = new Seller(null, "Pedro", "ph@gmail.com", new Date(), 4000.0, dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id: " + newSeller.getId());
		
		System.out.println();
		System.out.println("=== TEST 5: seller update ===");
		seller = sellerDao.findById(1);
		seller.setName("Marcos Antoni");
		sellerDao.update(seller);
		System.out.println("Update complete");

		sc.close();
	} 
}