package novelreadergui;

public class Truyen {

    private String tieuDe;
    private String tacGia;
    private String theLoai;
    private String noiDung;

    public Truyen(String tieuDe, String tacGia, String theLoai, String noiDung) {
        this.tieuDe = tieuDe;
        this.tacGia = tacGia;
        this.theLoai = theLoai;
        this.noiDung = noiDung;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public String getTacGia() {
        return tacGia;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public String getNoiDung() {
        return noiDung;
    }

    @Override
    public String toString() {
        return tieuDe + " - " + tacGia + " (" + theLoai + ")";
    }
}
