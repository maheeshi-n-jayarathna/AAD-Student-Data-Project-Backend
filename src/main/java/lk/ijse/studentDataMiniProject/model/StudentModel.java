package lk.ijse.studentDataMiniProject.model;

import lk.ijse.studentDataMiniProject.dto.StudentDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    public static Connection connection;

    public static List<StudentDTO> getAllStudent() {
        List<StudentDTO> studentDTOS = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement("Select * from student");
            ResultSet rst = pst.executeQuery();
            while (rst.next()){
                studentDTOS.add(new StudentDTO(
                        rst.getInt(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getInt(5)
                ));
            }
            return studentDTOS;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static StudentDTO saveStudent(StudentDTO studentDTO) {
        try {
            PreparedStatement ptm = connection.prepareStatement("INSERT INTO student (name, city, email, level) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ptm.setString(1, studentDTO.getName());
            ptm.setString(2, studentDTO.getCity());
            ptm.setString(3, studentDTO.getEmail());
            ptm.setInt(4, studentDTO.getLevel());
            ptm.executeUpdate();
            ResultSet rst = ptm.getGeneratedKeys();
            if (rst.next()) {
                int id = rst.getInt(1);
                studentDTO.setId(id);
                return studentDTO;
            }
            throw new SQLException();
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean updateStudent(StudentDTO studentDTO) {
        try {
            PreparedStatement ptm = connection.prepareStatement("UPDATE student SET name = ?, city = ?, email = ?, level = ? WHERE studentId=?");
            ptm.setString(1, studentDTO.getName());
            ptm.setString(2, studentDTO.getCity());
            ptm.setString(3, studentDTO.getEmail());
            ptm.setInt(4, studentDTO.getLevel());
            ptm.setInt(5, studentDTO.getId());

            return ptm.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteStudent(int id) {
        try {
            PreparedStatement ptm = connection.prepareStatement("DELETE FROM student WHERE id=?");
            ptm.setInt(1, id);

            return ptm.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
