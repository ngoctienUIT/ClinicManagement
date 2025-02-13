/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clinicmanagement;

import static clinicmanagement.Home.scaleImage;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dell
 */
public class PhieuKhamBenh extends javax.swing.JFrame {

    
    private boolean User = false;
    /**
     * Creates new form PhieuKhamBenh
     */
    public static String TenBenhNhan;
    public static String TenTrieuChung = "";
    public static int LoaiBenhChon = 0;
    public static boolean MoLanDau;
    public static String MaBenhNhan;
    public static String MaNhanVien = "NV001";
    private static String MaPhieuKhamBenh;
    private static String CMND = "";
    public PhieuKhamBenh() {
        initComponents();
        if (MoLanDau)
        {
            LoaiBenhChon = 0;
            TenTrieuChung = "";
            MoLanDau = false;
        }
        TrieuChung.setText(TenTrieuChung);
        this.setLocationRelativeTo(null);
        try {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT TenLoaiBenh FROM LOAIBENH");
            while (rs.next()) {
                LoaiBenh.addItem(rs.getString(1));
            }
            
            LoaiBenh.setSelectedIndex(LoaiBenhChon);
            rs = stm.executeQuery("SELECT MaPhieuKhamBenh FROM PHIEUKHAMBENH WHERE MaBenhNhan = '"+MaBenhNhan+"' AND MaNhanVien is null");
            while (rs.next())
            {
                MaPhieuKhamBenh = rs.getString(1);
            }
            rs = stm.executeQuery("SELECT TenThuoc, TenDonViTinh, SoLuongDung, TenCachDung, LieuDung FROM THUOC, CT_PHIEUKHAMBENH, CACHDUNG WHERE THUOC.MaThuoc = CT_PHIEUKHAMBENH.MaThuoc AND CACHDUNG.MaCachDung = THUOC.MaCachDung AND MaPhieuKhamBenh = '"+MaPhieuKhamBenh+"'");
            DefaultTableModel model = (DefaultTableModel) Table.getModel();
            int STT = 1;
            while (rs.next())
            {
                Vector vector = new Vector();
                vector.add(STT);
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                model.addRow(vector);
                STT++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
        textTenBenhNhan.setText(TenBenhNhan);
        NgayKham.setText(String.valueOf(LocalDate.now().getDayOfMonth()) + "/" + String.valueOf(LocalDate.now().getMonthValue()) + "/" + String.valueOf(LocalDate.now().getYear()));        
        jPanel3.setVisible(false);
    }
    
    public PhieuKhamBenh(String CMND) {
        initComponents();
        try {
            if (MoLanDau)
        {
            LoaiBenhChon = 0;
            TenTrieuChung = "";
            MoLanDau = false;
        }
            TrieuChung.setText(TenTrieuChung);
            this.CMND = CMND;
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT MaNhanVien, TenNhanVien FROM NHANVIEN WHERE CMND = '"+CMND+"'");
            while (rs.next())
            {
                MaNhanVien = rs.getString(1);
                NguoiKham.setText(rs.getString(2));
            }
            rs = stm.executeQuery("SELECT TenLoaiBenh FROM LOAIBENH");
            while (rs.next()) {
                LoaiBenh.addItem(rs.getString(1));
            }
            LoaiBenh.setSelectedIndex(LoaiBenhChon);
            rs = stm.executeQuery("SELECT MaPhieuKhamBenh FROM PHIEUKHAMBENH WHERE MaBenhNhan = '"+MaBenhNhan+"' AND MaNhanVien is null");
            while (rs.next())
            {
                MaPhieuKhamBenh = rs.getString(1);
            }
            rs = stm.executeQuery("SELECT TenThuoc, TenDonViTinh, SoLuongDung, TenCachDung, LieuDung FROM THUOC, CT_PHIEUKHAMBENH, CACHDUNG WHERE THUOC.MaThuoc = CT_PHIEUKHAMBENH.MaThuoc AND CACHDUNG.MaCachDung = THUOC.MaCachDung AND MaPhieuKhamBenh = '"+MaPhieuKhamBenh+"'");
            DefaultTableModel model = (DefaultTableModel) Table.getModel();
            int STT = 1;
            while (rs.next())
            {
                Vector vector = new Vector();
                vector.add(STT);
                vector.add(rs.getString(1));
                vector.add(rs.getString(2));
                vector.add(rs.getString(3));
                vector.add(rs.getString(4));
                vector.add(rs.getString(5));
                model.addRow(vector);
                STT++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString());
        }
        textTenBenhNhan.setText(TenBenhNhan);
        NgayKham.setText(String.valueOf(LocalDate.now().getDayOfMonth()) + "/" + String.valueOf(LocalDate.now().getMonthValue()) + "/" + String.valueOf(LocalDate.now().getYear()));        
        jPanel3.setVisible(false);
        RetriveData();
    }
    
    private void RetriveData() {
        if ("admin".equals(CMND)) {
            ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
            Anhdaidien.setIcon(iconnull);
            Tentaikhoan.setText("admin");
        } else {
            try {
                DatabaseConnection DTBC = new DatabaseConnection();
                Connection conn = DTBC.getConnection(this);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT TenNhanVien, HinhAnh, NHANVIEN.MaNhanVien, CHUCNANG.MaChucNang, TenChucNang FROM NHANVIEN, CHUCNANG, PHANQUYEN "
                        + "WHERE CMND = '" + CMND + "' AND PHANQUYEN.MaNhanVien = NHANVIEN.MaNhanVien AND CHUCNANG.MaChucNang = PHANQUYEN.MaChucNang");
                if (rs.next()) {
                    Tentaikhoan.setText(rs.getString("TenNhanVien"));
                    try {

                        URL url = getClass().getResource(rs.getString("HinhAnh"));
                        File file = new File(url.getPath());
                        BufferedImage master = ImageIO.read(file);
                        try {
                            master = scaleImage(64, 64, master);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        int diameter = Math.min(master.getWidth(), master.getHeight());
                        BufferedImage mask = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_INT_ARGB);

                        Graphics2D g2d = mask.createGraphics();
                        g2d.fillOval(0, 0, diameter - 1, diameter - 1);
                        g2d.dispose();

                        BufferedImage masked = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
                        g2d = masked.createGraphics();
                        int x = (diameter - master.getWidth()) / 2;
                        int y = (diameter - master.getHeight()) / 2;
                        g2d.drawImage(master, x, y, null);
                        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
                        g2d.drawImage(mask, 0, 0, null);
                        g2d.dispose();
                        ImageIcon icon = new ImageIcon(masked);
                        Anhdaidien.setIcon(icon);
                    } catch (InvalidPathException | NullPointerException | IOException ex) {
                        ImageIcon iconnull = new ImageIcon(getClass().getResource("/anh/NotSetAvt.png"));
                        Anhdaidien.setIcon(iconnull);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Có lỗi xảy ra", "Đăng nhập thất bại", 2);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Lỗi kết nối cơ sở dữ liệu", ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        Tentrang1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Anhdautrang = new javax.swing.JLabel();
        Tentrang = new javax.swing.JLabel();
        Tentaikhoan = new javax.swing.JLabel();
        Anhdaidien = new javax.swing.JLabel();
        Nutmuiten = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textTenBenhNhan = new javax.swing.JLabel();
        NguoiKham = new javax.swing.JLabel();
        NgayKham = new javax.swing.JLabel();
        TrieuChung = new javax.swing.JTextField();
        LoaiBenh = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(0, 166, 84));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thông tin cá nhân");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 11, -1, 49));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278996697_723755712095971_8418662915417084857_n.png"))); // NOI18N
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 14, -1, -1));

        jButton4.setBackground(new java.awt.Color(0, 166, 84));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Đổi mật khẩu");
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 69, 201, 46));

        jButton5.setBackground(new java.awt.Color(242, 111, 51));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Đăng xuất");
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 121, 200, 46));
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 65, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/278367228_415742123260148_2336724036194180860_n.png"))); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 121, 54, 46));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/277367234_720289712438638_7547041272784298626_n.png"))); // NOI18N
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 65, -1, 50));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 90, 280, 180));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 84, 42));
        jLabel1.setText("Họ tên:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 188, 122, -1));

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 99, 28));
        jButton2.setText("Quay lại");
        jButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 590, 168, 36));

        Tentrang1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Tentrang1.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang1.setText("PHIẾU KHÁM BỆNH\n");
        jPanel1.add(Tentrang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 100, 389, -1));

        jButton3.setBackground(new java.awt.Color(255, 204, 204));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 99, 28));
        jButton3.setText("Xuất hóa đơn");
        jButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 590, 168, 36));

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Thuốc", "Đơn vị tính", "Số lượng", "Cách dùng", "Liều dùng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table.setEnabled(false);
        jScrollPane1.setViewportView(Table);
        if (Table.getColumnModel().getColumnCount() > 0) {
            Table.getColumnModel().getColumn(0).setMaxWidth(50);
            Table.getColumnModel().getColumn(1).setMinWidth(150);
            Table.getColumnModel().getColumn(1).setPreferredWidth(150);
            Table.getColumnModel().getColumn(1).setMaxWidth(150);
            Table.getColumnModel().getColumn(2).setMinWidth(150);
            Table.getColumnModel().getColumn(2).setPreferredWidth(150);
            Table.getColumnModel().getColumn(2).setMaxWidth(150);
            Table.getColumnModel().getColumn(3).setMinWidth(100);
            Table.getColumnModel().getColumn(3).setPreferredWidth(100);
            Table.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 328, 970, 230));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 84, 42));
        jLabel2.setText("Triệu chứng:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 231, 122, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 84, 42));
        jLabel3.setText("Người Khám:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 274, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 84, 42));
        jLabel4.setText("Ngày khám:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 188, 183, -1));

        jPanel2.setBackground(new java.awt.Color(208, 242, 224));
        jPanel2.setPreferredSize(new java.awt.Dimension(744, 87));

        Anhdautrang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Untitled-5 1.png"))); // NOI18N
        Anhdautrang.setText("jLabel5");

        Tentrang.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentrang.setForeground(new java.awt.Color(0, 84, 42));
        Tentrang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tentrang.setText("PHIẾU KHÁM BỆNH\n");

        Tentaikhoan.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        Tentaikhoan.setForeground(new java.awt.Color(0, 84, 42));
        Tentaikhoan.setText("Lê Phi Long\n");

        Anhdaidien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/image 6.png"))); // NOI18N

        Nutmuiten.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Screenshot 2022-04-26 103146.png"))); // NOI18N
        Nutmuiten.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NutmuitenMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(Anhdautrang, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Tentrang, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 439, Short.MAX_VALUE)
                .addComponent(Anhdaidien, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Tentaikhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Nutmuiten)
                .addGap(34, 34, 34))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(Tentrang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Anhdautrang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Anhdaidien)
                            .addComponent(Tentaikhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Nutmuiten, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1171, 70));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 84, 42));
        jLabel5.setText("Dự đoán loại bệnh:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 231, 183, -1));

        textTenBenhNhan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textTenBenhNhan.setText("Nguyễn Đình Đức Thịnh");
        jPanel1.add(textTenBenhNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 188, 274, -1));

        NguoiKham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NguoiKham.setText("Lê Phi Long");
        jPanel1.add(NguoiKham, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 274, 274, -1));

        NgayKham.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NgayKham.setText("25/4/2022");
        jPanel1.add(NgayKham, new org.netbeans.lib.awtextra.AbsoluteConstraints(857, 188, 220, -1));

        TrieuChung.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(TrieuChung, new org.netbeans.lib.awtextra.AbsoluteConstraints(248, 228, 274, -1));

        LoaiBenh.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jPanel1.add(LoaiBenh, new org.netbeans.lib.awtextra.AbsoluteConstraints(857, 228, 220, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Anh/Group 32.png"))); // NOI18N
        jLabel7.setText("jLabel3");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel7MousePressed(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 330, 65, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DanhSachKhamBenh(CMND).setVisible(true);
            }
        });
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try
        {
            DatabaseConnection DTBC = new DatabaseConnection();
            Connection conn = DTBC.getConnection(this);
            Statement stm = conn.createStatement();
            String MaLoaiBenh = "NULL";
            ResultSet rs = stm.executeQuery("SELECT MaLoaiBenh FROM LOAIBENH WHERE TenLoaiBenh = N'"+LoaiBenh.getSelectedItem().toString()+"'");
            while (rs.next())
            {
                MaLoaiBenh = rs.getString(1);
            }
            String TrieuChungNhap = TrieuChung.getText();
            rs = stm.executeQuery("SELECT GiaTri FROM THAMSO WHERE TenThamSo = 'TienKham'");
            String TienKham = "NULL";
            while (rs.next())
            {
                TienKham = String.valueOf(rs.getInt(1));
            }
            rs = stm.executeQuery("SELECT SoLuongDung*DonGiaThuoc FROM CT_PHIEUKHAMBENH WHERE MaPhieuKhamBenh = '"+MaPhieuKhamBenh+"'");
            int TienThuoc = 0;
            while (rs.next())
            {
                TienThuoc = TienThuoc + rs.getInt(1);
            }
            stm.executeUpdate("UPDATE PHIEUKHAMBENH SET MaLoaiBenh = '"+MaLoaiBenh+"', TrieuChung = N'"+TrieuChungNhap+"', TienKham = "+TienKham+", TienThuoc = "+String.valueOf(TienThuoc)+", MaNhanVien = '"+MaNhanVien+"' WHERE MaPhieuKhamBenh = '"+MaPhieuKhamBenh+"'");
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    
                    ChiTietHoaDon dialog = new ChiTietHoaDon(new DanhSachKhamBenh(), true, CMND);
                    dialog.setMaPhieuKhamBenh(MaPhieuKhamBenh);
                    dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    for (WindowListener wl : dialog.getWindowListeners()) {
                        dialog.removeWindowListener(wl);
                    }
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            dialog.dispose();
                            DanhSachKhamBenh frame = new DanhSachKhamBenh(CMND);
                            frame.setVisible(true);
                        }
                    });
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                }
            });
            this.dispose();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, e.toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserInformation dialog = new UserInformation(new javax.swing.JFrame(), true);
                dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                for (WindowListener wl : dialog.getWindowListeners()) {
                    dialog.removeWindowListener(wl);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        dialog.dispose();
                        MedicineUsageManagement frame = new MedicineUsageManagement();
                        frame.setVisible(true);
                    }
                });
                dialog.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ClinicManagement form = new ClinicManagement();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void NutmuitenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NutmuitenMouseClicked
        if (User == false) {
            jPanel3.setVisible(true);
            User = true;
        } else {
            jPanel3.setVisible(false);
            User = false;
        }           // TODO add your handling code here:
    }//GEN-LAST:event_NutmuitenMouseClicked

    private void jLabel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MousePressed
        LoaiBenhChon = LoaiBenh.getSelectedIndex();
        TenTrieuChung = TrieuChung.getText();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    KeThuoc dialog = new KeThuoc(CMND);
                    KeThuoc.MaPhieuKhamBenh = MaPhieuKhamBenh;
                    dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    for (WindowListener wl : dialog.getWindowListeners()) {
                        dialog.removeWindowListener(wl);
                    }
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                            dialog.dispose();
                            PhieuKhamBenh frame = new PhieuKhamBenh(CMND);
                            frame.setVisible(true);
                        }
                    });
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                } catch (Exception e) {

                }
            }
        });
        this.dispose();
    }//GEN-LAST:event_jLabel7MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PhieuKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhieuKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhieuKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhieuKhamBenh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PhieuKhamBenh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Anhdaidien;
    private javax.swing.JLabel Anhdautrang;
    private javax.swing.JComboBox<String> LoaiBenh;
    private javax.swing.JLabel NgayKham;
    private javax.swing.JLabel NguoiKham;
    private javax.swing.JLabel Nutmuiten;
    private javax.swing.JTable Table;
    private javax.swing.JLabel Tentaikhoan;
    private javax.swing.JLabel Tentrang;
    private javax.swing.JLabel Tentrang1;
    private javax.swing.JTextField TrieuChung;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel textTenBenhNhan;
    // End of variables declaration//GEN-END:variables
}
