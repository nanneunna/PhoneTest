package FirstTest;

import java.sql.*;
import java.util.Scanner;

class Data
{
    String name, phoneNumber, address;
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}
}
class SQLC
{
    private static Connection conn;
    private static PreparedStatement pstmt;

    SQLC() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phonetest","root","1234");
    }

    void DataInsert(Data d)
    {
        try {
            pstmt = conn.prepareStatement(" insert into phone values (?,?,?);");
            pstmt.setString(1, d.getName());
            pstmt.setString(2, d.getPhoneNumber());
            pstmt.setString(3, d.getAddress());
            pstmt.executeUpdate();
            System.out.println("입력이 완료되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void selectAll() throws SQLException {
        String sql = "select * from phone;";
        pstmt=conn.prepareStatement(sql);
        ResultSet rs =pstmt.executeQuery();
        int count=0;
        while(rs.next()){
            System.out.print(rs.getString("name")+" / ");
            System.out.print(rs.getString("phoneNUmber")+" / ");
            System.out.print(rs.getString("address"));
            System.out.println();
            count++;
        }
        if(count==0){
            System.out.println("전화번호부에 등록된 정보가 없습니다.");
        }
        else {
            System.out.println("총 "+count+"건의 정보가 등록되어 있습니다.");
        }
    }
    void findSelect(String name) throws SQLException {
        String sql = "select * from phone where name = ?;";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, name);
        ResultSet rs =pstmt.executeQuery();
        int count = 0;
        while(rs.next()){
            System.out.print(rs.getString("name")+" / ");
            System.out.print(rs.getString("phoneNUmber")+" / ");
            System.out.print(rs.getString("address"));
            System.out.println();
            count++;
        }
        if(count==0){
            System.out.println("찾는 이름이 없습니다.");
        }

    }

    void deleteSelect(String name) throws SQLException {
        String sql = "delete from phone where name = ?;";
        pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, name);
        int num = pstmt.executeUpdate();
        if(num==0){
            System.out.println("찾는 이름이 없습니다.");
        }
        else {
            System.out.println("삭제가 완료되었습니다.");
        }
    }
}
class InputClass
{
    Data valueReturn() {
        Data d = new Data();

        Scanner scS = new Scanner(System.in);

        System.out.print("이름을 입력하세요 : ");
        d.setName(scS.nextLine());
        System.out.print("전화번호를 입력하세요 : ");
        d.setPhoneNumber(scS.nextLine());
        System.out.print("주소를 입력하세요 : ");
        d.setAddress(scS.nextLine());
        return d;

    }
    String findString(){
        Scanner scS = new Scanner(System.in);
        System.out.println("이름을 입력하세요 : ");
        return scS.nextLine();
    }
}

public class PhoneTest {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        SQLC sq = new SQLC();
        InputClass ic = new InputClass();
        while(true)
        {
            System.out.print("1.입력 2.검색 3.삭제 4.출력 5.종료 : ");
            int num = sc.nextInt();
            if(num==1) {
                sq.DataInsert(ic.valueReturn());
            }
            else if(num == 2){
                sq.findSelect(ic.findString());
            }
            else if(num == 3){
                sq.deleteSelect(ic.findString());
            }
            else if(num == 4){
                sq.selectAll();
            }
            else if(num == 5){
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            else {
                System.out.println("잘못된 입력입니다.");
            }
            System.out.println();
        }
    }
}
