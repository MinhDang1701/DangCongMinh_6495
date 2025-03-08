package novelreadergui;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TruyenManager {

    private List<Truyen> truyenList = new ArrayList<>();
    private File file = new File("truyen.txt");

    public void themTruyen(Truyen truyen) {
        truyenList.add(truyen);
        luuToiFile();
    }

    public void xoaTruyen(int index) {
        if (index >= 0 && index < truyenList.size()) {
            truyenList.remove(index);
            luuToiFile();
        }
    }

    public void suaTruyen(int index, Truyen truyen) {
        if (index >= 0 && index < truyenList.size()) {
            truyenList.set(index, truyen);
            luuToiFile();
        }
    }

    public List<Truyen> timKiem(String tieuDe) {
        List<Truyen> ketQua = new ArrayList<>();
        for (Truyen truyen : truyenList) {
            if (truyen.getTieuDe().toLowerCase().contains(tieuDe.toLowerCase())) {
                ketQua.add(truyen);
            }
        }
        return ketQua;
    }

    public void luuToiFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Truyen truyen : truyenList) {
                writer.write(truyen.getTieuDe() + "," + truyen.getTacGia() + "," + truyen.getTheLoai() + "," + truyen.getNoiDung());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void docTuFile() {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        truyenList.add(new Truyen(parts[0], parts[1], parts[2], parts[3]));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Truyen> getTruyenList() {
        return truyenList;
    }
}
