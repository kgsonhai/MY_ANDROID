    package com.example.cuahangonline.ultil;

    import retrofit2.Retrofit;

    public class Server {
    public static String localhost = "website-chgiay.000webhostapp.com/admin";
    public static String duongdanloaiSP = "https://"+localhost+"/getloaisp.php";
    public static String duongdanSanPhamMoiNhat = "https://"+localhost+"/getspmoinhat.php";
    public static String duongdanGiay= "https://"+localhost+"/getsanpham.php?page=";
    public static String duongdanLogin= "https://"+localhost+"/getUsername.php";
    public static String duongdandonhang= "https://"+localhost+"/postDonHang.php";
    public static String duongdanchitietdonhang= "https://"+localhost+"/postChiTietDonHang.php";
    public static String duongdanDanhMuc= "https://"+localhost+"/getDanhMuc.php";
    public static String duongdanSanPhamTheoDanhmucc= "https://"+localhost+"/getSanPhamtheoDanhmuc.php?page=";
    public static String duongdanSanPhamTimKiemSP= "https://"+localhost+"/getSanPhamTimKiem.php?page=";
    public static String duongdanCountSoSP= "https://"+localhost+"/getCountField.php";
    public static String duongdanCountSLSP= "https://"+localhost+"/getCountSP.php";
    public static String duongdanSanPhamLocSP= "https://"+localhost+"/getFilter.php?page=";
    public static String duongdanSignin= "https://"+localhost+"/postDangKy.php";
    public static String duongdanEditInforAC= "https://"+localhost+"/updateInforAC.php";




}
