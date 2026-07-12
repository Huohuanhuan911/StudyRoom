package com.studyroom.config;

import com.studyroom.entity.*;
import com.studyroom.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            BuildingRepository buildingRepository,
            ClassroomRepository classroomRepository,
            SeatRepository seatRepository) {
        return args -> {
            initUsers(userRepository, passwordEncoder);
            initBuildings(buildingRepository);
            initClassrooms(classroomRepository);
            initSeats(seatRepository, classroomRepository);
        };
    }

    private void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = User.builder()
                    .username("admin")
                    .name("管理员")
                    .password(passwordEncoder.encode("123456"))
                    .role("ADMIN")
                    .creditScore(100)
                    .violationCount(0)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ 管理员账号创建成功！账号：admin，密码：123456");
        } else {
            System.out.println("✅ 管理员账号已存在，无需重复创建");
        }

        if (userRepository.findByUsername("22920242201234").isEmpty()) {
            User student = User.builder()
                    .username("22920242201234")
                    .name("张三")
                    .password(passwordEncoder.encode("123456"))
                    .role("STUDENT")
                    .creditScore(100)
                    .violationCount(0)
                    .college("计算机学院")
                    .major("软件工程")
                    .grade("2024级")
                    .email("zhangsan@example.com")
                    .build();
            userRepository.save(student);
            System.out.println("✅ 学生账号创建成功！账号：22920242201234");
        } else {
            System.out.println("✅ 学生账号已存在，无需重复创建");
        }

        if (userRepository.findByUsername("22920242201235").isEmpty()) {
            User student = User.builder()
                    .username("22920242201235")
                    .name("李四")
                    .password(passwordEncoder.encode("123456"))
                    .role("STUDENT")
                    .creditScore(100)
                    .violationCount(0)
                    .college("信息学院")
                    .major("计算机科学与技术")
                    .grade("2024级")
                    .email("lisi@example.com")
                    .build();
            userRepository.save(student);
            System.out.println("✅ 学生账号创建成功！账号：22920242201235");
        } else {
            System.out.println("✅ 学生账号已存在，无需重复创建");
        }

        if (userRepository.findByUsername("22920242201236").isEmpty()) {
            User student = User.builder()
                    .username("22920242201236")
                    .name("王五")
                    .password(passwordEncoder.encode("123456"))
                    .role("STUDENT")
                    .creditScore(100)
                    .violationCount(0)
                    .college("电子学院")
                    .major("电子信息工程")
                    .grade("2023级")
                    .email("wangwu@example.com")
                    .build();
            userRepository.save(student);
            System.out.println("✅ 学生账号创建成功！账号：22920242201236");
        } else {
            System.out.println("✅ 学生账号已存在，无需重复创建");
        }
    }

    private void initBuildings(BuildingRepository buildingRepository) {
        if (buildingRepository.count() == 0) {
            buildingRepository.save(Building.builder()
                    .name("学武楼（1号楼）")
                    .location("校园东区")
                    .description("教学楼")
                    .deleted(false)
                    .build());
            buildingRepository.save(Building.builder()
                    .name("德旺图书馆")
                    .location("校园中心")
                    .description("图书馆自习区域")
                    .deleted(false)
                    .build());
            buildingRepository.save(Building.builder()
                    .name("文宣楼（4号楼）")
                    .location("校园西区")
                    .description("教学楼")
                    .deleted(false)
                    .build());
            buildingRepository.save(Building.builder()
                    .name("坤銮楼（2号楼）")
                    .location("校园北区")
                    .description("教学楼")
                    .deleted(false)
                    .build());
            buildingRepository.save(Building.builder()
                    .name("西部片区2号楼")
                    .location("校园西区")
                    .description("教学楼")
                    .deleted(false)
                    .build());
            System.out.println("✅ 楼栋数据初始化完成（5个楼栋）");
        } else {
            System.out.println("✅ 楼栋数据已存在，无需重复创建");
        }
    }

    private void initClassrooms(ClassroomRepository classroomRepository) {
        if (classroomRepository.count() == 0) {
            classroomRepository.save(Classroom.builder()
                    .buildingId(1L)
                    .name("G102")
                    .floor(1)
                    .capacity(30)
                    .hasAirConditioning(true)
                    .description("带空调教室")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(1L)
                    .name("G103")
                    .floor(1)
                    .capacity(25)
                    .hasAirConditioning(true)
                    .description("带空调教室")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(1L)
                    .name("G109")
                    .floor(1)
                    .capacity(20)
                    .hasAirConditioning(false)
                    .description("普通教室")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(1L)
                    .name("G110")
                    .floor(1)
                    .capacity(35)
                    .hasAirConditioning(true)
                    .description("带空调教室")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(2L)
                    .name("1楼A区")
                    .floor(1)
                    .capacity(40)
                    .hasAirConditioning(true)
                    .description("带空调区域")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(2L)
                    .name("1楼B区")
                    .floor(1)
                    .capacity(35)
                    .hasAirConditioning(true)
                    .description("带空调区域")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(2L)
                    .name("1楼C区")
                    .floor(1)
                    .capacity(30)
                    .hasAirConditioning(false)
                    .description("普通区域")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(2L)
                    .name("2楼A区")
                    .floor(2)
                    .capacity(45)
                    .hasAirConditioning(true)
                    .description("带空调区域")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(2L)
                    .name("2楼B区")
                    .floor(2)
                    .capacity(40)
                    .hasAirConditioning(true)
                    .description("带空调区域")
                    .deleted(false)
                    .build());

            classroomRepository.save(Classroom.builder()
                    .buildingId(2L)
                    .name("2楼C区")
                    .floor(2)
                    .capacity(35)
                    .hasAirConditioning(false)
                    .description("普通区域")
                    .deleted(false)
                    .build());

            System.out.println("✅ 教室数据初始化完成（10个教室）");
        } else {
            System.out.println("✅ 教室数据已存在，无需重复创建");
        }
    }

    private void initSeats(SeatRepository seatRepository, ClassroomRepository classroomRepository) {
        if (seatRepository.count() == 0) {
            createSeatsForRoom(seatRepository, 1L, "W-G102", 30, 9);
            createSeatsForRoom(seatRepository, 2L, "W-G103", 25, 7);
            createSeatsForRoom(seatRepository, 3L, "W-G109", 20, 6);
            createSeatsForRoom(seatRepository, 4L, "W-G110", 35, 10);

            createSeatsForRoom(seatRepository, 5L, "D-1A", 40, 12);
            createSeatsForRoom(seatRepository, 6L, "D-1B", 35, 10);
            createSeatsForRoom(seatRepository, 7L, "D-1C", 30, 9);
            createSeatsForRoom(seatRepository, 8L, "D-2A", 45, 13);
            createSeatsForRoom(seatRepository, 9L, "D-2B", 40, 12);
            createSeatsForRoom(seatRepository, 10L, "D-2C", 35, 10);

            System.out.println("✅ 座位数据初始化完成");
        } else {
            System.out.println("✅ 座位数据已存在，无需重复创建");
        }
    }

    private void createSeatsForRoom(SeatRepository seatRepository, Long classroomId, String prefix, int capacity, int socketCount) {
        for (int i = 1; i <= capacity; i++) {
            String seatNumber = String.format("%s-%02d", prefix, i);
            boolean hasSocket = i <= socketCount;

            seatRepository.save(Seat.builder()
                    .classroomId(classroomId)
                    .seatNumber(seatNumber)
                    .row((i - 1) / 6 + 1)
                    .col((i - 1) % 6 + 1)
                    .hasSocket(hasSocket)
                    .status("AVAILABLE")
                    .deleted(false)
                    .build());
        }
    }
}