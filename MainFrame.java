package novelreadergui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {
    private TruyenManager truyenManager;
    private DefaultListModel<Truyen> listModel;
    private JList<Truyen> list;

    public MainFrame() {
        truyenManager = new TruyenManager();
        truyenManager.docTuFile();

        setTitle("Quản Lý Truyện");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        for (Truyen truyen : truyenManager.getTruyenList()) {
            listModel.addElement(truyen);
        }

        list = new JList<>(listModel);
        list.setFont(new Font("Arial", Font.PLAIN, 16));

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list.getSelectedIndex();
                    if (index != -1) docTruyen(listModel.get(index));
                }
            }
        });

        JPanel panel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnTim = new JButton("Tìm");

        btnThem.addActionListener(e -> themTruyen());
        btnSua.addActionListener(e -> suaTruyen());
        btnXoa.addActionListener(e -> xoaTruyen());
        btnTim.addActionListener(e -> timTruyen());

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnTim);

        add(new JScrollPane(list), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void themTruyen() {
        String tieuDe = JOptionPane.showInputDialog("Tiêu đề:");
        String tacGia = JOptionPane.showInputDialog("Tác giả:");
        String theLoai = JOptionPane.showInputDialog("Thể loại:");
        String noiDung = JOptionPane.showInputDialog("Nội dung:");
        if (tieuDe != null && tacGia != null && theLoai != null && noiDung != null) {
            Truyen truyen = new Truyen(tieuDe, tacGia, theLoai, noiDung);
            truyenManager.themTruyen(truyen);
            listModel.addElement(truyen);
        }
    }

    private void suaTruyen() {
        int index = list.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Chọn truyện để sửa!");
            return;
        }
        Truyen truyen = listModel.get(index);
        String tieuDe = JOptionPane.showInputDialog("Tiêu đề:", truyen.getTieuDe());
        String tacGia = JOptionPane.showInputDialog("Tác giả:", truyen.getTacGia());
        String theLoai = JOptionPane.showInputDialog("Thể loại:", truyen.getTheLoai());
        String noiDung = JOptionPane.showInputDialog("Nội dung:", truyen.getNoiDung());

        if (tieuDe != null && tacGia != null && theLoai != null && noiDung != null) {
            Truyen truyenMoi = new Truyen(tieuDe, tacGia, theLoai, noiDung);
            truyenManager.suaTruyen(index, truyenMoi);
            listModel.set(index, truyenMoi);
        }
    }

    private void xoaTruyen() {
        int index = list.getSelectedIndex();
        if (index != -1) {
            truyenManager.xoaTruyen(index);
            listModel.remove(index);
        }
    }

    private void timTruyen() {
        String tuKhoa = JOptionPane.showInputDialog("Nhập tiêu đề:");
        if (tuKhoa != null) {
            List<Truyen> ketQua = truyenManager.timKiem(tuKhoa);
            listModel.clear();
            for (Truyen truyen : ketQua) {
                listModel.addElement(truyen);
            }
        }
    }

    private void docTruyen(Truyen truyen) {
        JDialog dialog = new JDialog(this, "Đọc Truyện", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea(truyen.getNoiDung());
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));

        dialog.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnDong);

        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
