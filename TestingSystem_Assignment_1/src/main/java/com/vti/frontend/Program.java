package com.vti.frontend;

import com.vti.backend.DepartmentRepository;
import com.vti.entity.Group;
import utils.ScannerUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        int option = 0;
        DepartmentRepository departmentRepository = new DepartmentRepository();
        while (true) {
            System.out.println("Mời nhập chức năng: ");
            System.out.println("1. CreateGroups");
            System.out.println("2. getAllGroups");
            System.out.println("3. getGroupByID");
            System.out.println("4. getGroupByName");
            System.out.println("5. updateGroup");
            System.out.println("6. deleteGroup");
            System.out.println("7. isGroupExistsByID");
            System.out.println("8. isGroupExistsByName");
            System.out.println("0. Exit");

            option = ScannerUtils.inputNumber();
            ScannerUtils.inputString();

            switch (option) {
                case 1:
                    System.out.println("Enter group name: ");
                    String name = ScannerUtils.inputString();
                    departmentRepository.createGroups(new Group(name));
                    break;
                case 2:
                    System.out.println(departmentRepository.getAllGroups());
                    break;
                case 3:
                    System.out.println("Enter id to find: ");
                    int id = ScannerUtils.inputNumber();
                    System.out.println(departmentRepository.findGroupById(id));
                    break;
                case 4:
                    System.out.println("Enter a name to find: ");
                    String groupName = ScannerUtils.inputString();
                    System.out.println(departmentRepository.findGroupByName(groupName));
                    break;
                case 5:
                    System.out.println("Enter a id to update: ");
                    id = ScannerUtils.inputNumber();
                    ScannerUtils.inputString();
                    System.out.println("Enter a name to update:");
                    groupName = ScannerUtils.inputString();

                    Group group = new Group(id, groupName);
                    departmentRepository.updateGroup(group);
                    break;
                case 6:
                    System.out.println("Enter a id to delete: ");
                    id = ScannerUtils.inputNumber();
                    departmentRepository.deleteGroupById(id);
                    break;
                case 7:
                    System.out.println("Enter a id to check: ");
                    id = ScannerUtils.inputNumber();
                    if (departmentRepository.checkGroupById(id)) {
                        System.out.println(departmentRepository.checkGroupById(id));
                    }
                    break;
                case 8:
                    System.out.println("Enter a name to check: ");
                    groupName = ScannerUtils.inputString();
                    if (departmentRepository.checkGroupByName(groupName)) {
                        System.out.println(departmentRepository.checkGroupByName(groupName));
                    }
                    break;
                case 0:
                    System.out.println("Program is closing....");
                    return;
            }
        }
    }
}
