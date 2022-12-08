package com.example.demo.DAO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.sql.*;
import java.util.*;

@Controller
public class TestController {
    @GetMapping("/pets")
    public String getAllPet(Model model) throws IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        List<Pet> pets = new ArrayList<Pet>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/animal", "root", "");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM `pet`");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String species = resultSet.getString("species");
                int age = resultSet.getInt("age");
                int neutered = resultSet.getInt("neutered");
                pets.add(new Pet(id, name, species,age, neutered == 0 ? false : true));
            }
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pets", pets);
        return "pet";
    }

    @GetMapping("/pet/{id}")
    public String getPet(Model model, @PathVariable String id) {
        model.addAttribute("id", id);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet result = null;

        Pet pet = new Pet();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/animal", "root", "");
            ps = connection.prepareStatement("select * from pet where id = ?");
            ps.setInt(1, Integer.valueOf(id));
            result = ps.executeQuery();
            while (result.next()) {
                pet.setId(result.getInt("id"));
                pet.setName(result.getString("name"));
                pet.setSpecies(result.getString("species"));
                pet.setAge(result.getInt("age"));
                pet.setNeutered(result.getInt("neutered") != 0 ? true : false);
            }
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("pet", pet);
        return "pet-detail";
    }

    @PostMapping("/pet/save/{id}")
    public String addPet(Pet pet, @PathVariable String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        int result = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/animal", "root", "");
            ps = connection.prepareStatement("INSERT INTO pet(name,species,age,neutered) VALUES ( ?, ?, ?, ?)");
            if(pet.getName()==""|| pet.getSpecies()=="" ||pet.getAge()==0){
                return "error";
            }else{
                ps.setString(1, pet.getName());
                ps.setString(2,  pet.getSpecies());
                ps.setInt(3, pet.getAge());
                ps.setInt(4, pet.isNeutered() ? 1 : 0);
                result = ps.executeUpdate();

                ps.close();
                connection.close();
                return "redirect:/pets";
            }
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @PutMapping("/pet/save/{id}")
    public String updatePet(Pet pet, @PathVariable String id) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/animal", "root", "");
            ps = connection.prepareStatement("UPDATE pet SET name=?,species=?,age=?,neutered=? WHERE id=?");
            if(pet.getName()==""|| pet.getSpecies()==""||pet.getAge()==0){
                return "error";
            }else {
                ps.setString(1, pet.getName());
                ps.setString(2,  pet.getSpecies());
                ps.setInt(3, pet.getAge());
                ps.setInt(4, pet.isNeutered() ? 1 : 0);
                ps.setInt(5, Integer.valueOf(pet.getId()));
                ps.executeUpdate();
                ps.close();
                connection.close();
                return "redirect:/pets";
            }
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }

    @DeleteMapping("pet/delete/{id}")
    public String deletePet (@PathVariable String id ){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/animal", "root", "");
            ps = connection.prepareStatement("delete from pet where id =?");
            ps.setInt(1, Integer.valueOf(id));
            ps.executeUpdate();
            ps.close();
            connection.close();
            return "redirect:/pets";
        } // End of try block
        catch (Exception e) {
            e.printStackTrace();
        }
        return "error"; // tạo trang Error
    }

}


