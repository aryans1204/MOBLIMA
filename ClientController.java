import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;

public class ClientController {
    private String fileName;

    ClientController(String fileName){
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    //Inserting Customers name, age, username and password into the database
    public void insertCustomerToDB(String name, int age, String username, String password){
        ArrayList<Customer> customers = new ArrayList<>();
        Customer newCustomer = new Customer(name, age, username, password);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        File f = new File(fileName);
        if(f.exists()) {
            customers = this.getCustomerFromDB();
            customers.add(newCustomer);
        }
        else{
            System.out.println("File: " + fileName + " does not exist");
            System.out.println("Creating new DB");
        }

        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(customers);
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }

    //Getting all customer's details from the database
    public ArrayList<Customer> getCustomerFromDB(){
        ArrayList<Customer> customers = null;
        FileInputStream fis = null;
        ObjectInputStream in = null;

        try{
            fis = new FileInputStream(fileName);
            in = new ObjectInputStream(fis);
            customers = (ArrayList<Customer>) in.readObject();
            in.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        return customers;
    }

    public boolean checkUsernameExist(String username) {
        ArrayList<Customer> customers = null;
        File f = new File(fileName);
        if(f.exists()) {
            customers = this.getCustomerFromDB();//Read in existing data in DB
            if(customers.size()> 0) {
                for(Customer customer: customers)
                    if(customer.getUsername() == username)
                        return true;
            }
        }
//        else
//            System.out.println("File: " + fileName + " does not exist");

        return false;
    }

    //public boolean updateCustomerDB(){ return true;}
    // Don't think there is a need to update customer DB

    public boolean removeCustomer(String name, int age) {
        ArrayList<Customer> customers = null;
        File f = new File(fileName);
        if(f.exists()){
            customers = this.getCustomerFromDB();
            for(int i=0; i<customers.size();i++){
                if(customers.get(i).getName() == name && customers.get(i).getAge() == age){
                    customers.remove(i);
                    return true;
                }
            }
            System.out.println("Name not found in database");
        }
        else
            System.out.println("File: " + fileName + " does not exist");

        return false;
    }
}
