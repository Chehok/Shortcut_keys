import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.io.IOException;

public class Shortcut_keys extends JFrame{ //Shortcut_keys 프로그램에 JFrame 을 호출한다.
    Font font = new Font( "맑은 고딕", Font.PLAIN, 14); // 글씨체
    static JLabel la = new JLabel();

    Container c = getContentPane(); // 컨테이너 선언
    JButton b[] = new JButton[10]; // JButton 10개 선언

    private Shortcut_keys() {
        setTitle("단축키 프로그램"); //JFrame의 이름을 설정한다.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x키를 누르면 프로세스 종료
        GridLayout(); // 버튼을 생성하는 클래스

        setSize(320, 220); // 창의 크기
        setResizable(false); // 창의 크기를 조정하지 못하게 설정
        setVisible(true); // 실행되면 창이 뜸.
        Color co = new Color(205, 209, 255); // JFrame 의 배경색을 정해줌.
        c.setBackground(co); // 배경색 적용

        //void windowDeactive

    }

    private void GridLayout() { // 버튼 생성
        FileManager f = new FileManager(); // 파일 관리자 호출
        ButtonArray[] ButtonList = new ButtonArray[10]; // JButton 객체 배열 호출

        GridLayout grid = new GridLayout(4, 3, 7, 7); // 행4 열3 (4X3), 버튼간간격 7
        c.setLayout(grid); // Layout 설정(grid)

        for (int i = 0; i < 10; i++) { // 10번 반복
            ButtonList[i] = new ButtonArray(f.bring_name(i + 1), i); // 객체 배열을 생성한다.
            ButtonList[i].subButton(); // GUI 버튼 설정
        }
        JLabel input = new JLabel("입력 값: ");
        input.setFont(font); // 미리 저장해둔 폰트 설정
        c.add(input); // 컨테이너에 문구 추가
        c.add(la); // 키보드가 입력한 값을 컨테이너에 출력한다.
        c.addKeyListener(new MyActionListener()); // 이벤트 감지(버튼, 키보드) 함수 선언
        c.setFocusable(true); // 이벤트 감지 함수에 포커스를 준다.
    }

    public static class FileManager { // 파일 매니저
        InputStreamReader isr = null;
        FileInputStream fis = null;
        File nfile = new File("name.txt");//파일 객체 생성
        File lfile = new File("location.txt");

        void FileCheck() { //파일이 존재하지 않을 때 생성
            boolean lExist = lfile.exists();
            try {
                String dollar = "$$$$$$$$$$";
                if (!lExist) {
                    File lfile = new File("location.txt");
                    FileWriter fw = new FileWriter(lfile);
                    fw.write(dollar);
                    fw.flush();
                    fw.close();

                    File nfile = new File("name.txt");
                    fw = new FileWriter(nfile);
                    fw.write(dollar);
                    fw.flush();
                    fw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void Save(String name, String lo, int num) { //파일 저장
            try {
                StringBuffer sb = new StringBuffer();

                //경로저장
                fis = new FileInputStream("location.txt");
                isr = new InputStreamReader(fis, "MS949");
                int i;
                int count = 0;

                while ((i = isr.read()) != -1) {//파일내용+저장하려는경로를 스트링버퍼에 저장
                    if ((char) i == '$') {
                        count++; //방 번호 카운트
                    }
                    if (count == num) { //내가 찾던 방이면
                        sb.append((char) i);
                        sb.append(lo); //경로 입력
                    } else if (count != num) { //다른방이면
                        sb.append((char) i); // $만추가하고 넘어감
                    }
                }

                String copy = sb.toString();//스트링값에 스트링버퍼저장
                isr.close();
                fis.close();//파일닫기

                FileWriter fw = new FileWriter(lfile);// 기존의 파일을 대체함

                fw.write(copy);
                fw.flush(); //원래 파일을 대체한다

                fw.close(); // 객체 닫기

                //이름을 저장할때 재활용할 변수 초기화
                copy = null;
                sb.setLength(0);
                count = 0;

                //이름저장
                fis = new FileInputStream("name.txt");//이름저장
                isr = new InputStreamReader(fis, "MS949"); //한글도 읽을 수 있음
                while ((i = isr.read()) != -1) {
                    if ((char) i == '$') {
                        count++;
                    }
                    if (count == num) {
                        sb.append((char) i);
                        sb.append(name); //이름 입력
                    } else if (count != num) {
                        sb.append((char) i);
                    }

                }
                copy = sb.toString();
                isr.close();
                fis.close();

                fw = new FileWriter(nfile);// 기존의 파일을 대체함

                fw.write(copy);
                fw.flush(); //파일안에 문자열 쓰기

                fw.close(); // 객체 닫기
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void Delete(int num) { // 파일 지울 때
            try {
                StringBuffer sb = new StringBuffer();

                //경로저장
                fis = new FileInputStream("location.txt");
                isr = new InputStreamReader(fis, "MS949");
                int i;
                int count = 0;

                while ((i = isr.read()) != -1) {//파일내용+저장하려는경로를 스트링버퍼에 저장
                    if ((char) i == '$') {
                        count++;
                        sb.append((char) i);
                        continue;
                    }
                    if (count != num) {
                        sb.append((char) i);
                    }

                }
                String copy = sb.toString();//스트링값에 스트링버퍼저장
                isr.close();
                fis.close();//파일닫기

                FileWriter fw = new FileWriter(lfile);// 기존의 파일을 대체함

                fw.write(copy);
                fw.flush(); //원래 파일을 대체한다

                fw.close();

                copy = null;
                sb.setLength(0);
                count = 0;

                fis = new FileInputStream("name.txt");
                isr = new InputStreamReader(fis, "MS949");
                while ((i = isr.read()) != -1) {//파일내용+저장하려는경로를 스트링버퍼에 저장
                    if ((char) i == '$') {
                        count++;
                        sb.append((char) i);
                        continue;
                    }
                    if (count != num) {
                        sb.append((char) i);
                    }
                }
                copy = sb.toString();//스트링값에 스트링버퍼저장
                isr.close();
                fis.close();//파일닫기

                fw = new FileWriter(nfile);// 기존의 파일을 대체함

                fw.write(copy);
                fw.flush(); //원래 파일을 대체한다

                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        String bring_location(int num) { //경로를 가져올 때
            String lo = null;
            int count = 0;
            int nullcount = 0;
            StringBuffer sb = new StringBuffer();
            try {
                fis = new FileInputStream("location.txt");
                isr = new InputStreamReader(fis, "MS949");
                int i;
                while ((i = isr.read()) != -1) {//경로 스트링버퍼에 저장
                    if ((char) i == '$') {
                        count++;
                    }
                    if (count == num) {
                        if ((char) i == '$') {
                            continue;
                        } else {
                            sb.append((char) i);
                            nullcount += 1;
                        }
                    }
                }
                isr.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            lo = sb.toString(); //경로를 스트링으로 넘겨줌
            if (nullcount == 0) { //경로가 비어있으면
                lo = null;
            }
            return lo;
        }

        String bring_name(int num) { //이름을 가져올 때
            String na = null;
            int count = 0;
            int nullcount = 0;
            StringBuffer sb = new StringBuffer();
            try {
                fis = new FileInputStream("name.txt");
                isr = new InputStreamReader(fis, "MS949");
                int i;
                while ((i = isr.read()) != -1) {
                    if ((char) i == '$') {
                        count++;
                    }
                    if (count == num) {
                        if ((char) i == '$') {
                            continue;
                        } else {
                            sb.append((char) i);
                            nullcount += 1;
                        }
                    }
                }
                isr.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            na = sb.toString();
            if (nullcount == 0) {
                na = null;
            }
            return na;
        }
    }

    public class ButtonArray { // 버튼에 들어갈 정보를 관리하는 배열 클래스
        String ButtonBlankName[] = {"Crtl + 1", "Crtl + 2", "Crtl + 3", "Crtl + 4", "Crtl + 5", "Crtl + 6", "Crtl + 7", "Crtl + 8", "Crtl + 9", "Crtl + 0"}; // 빈 버튼에 넣을 기본 이름들
        // 만약 파일 관리자에 저장된 파일이 없을 경우 버튼의 이름을 임의로 지정한다.
        String na; // 이름 저장
        int num; // 번호 저장

        public ButtonArray(String na, int num) { // 이름과 번호를 받아 배열로 선언한다.
            this.na = na;
            this.num = num;
        }

        public String name() { // 각 JButton에 이름 선언(초기 설정)
            if (this.na == null) { // 저장된 파일(이름)이 없는 경우
                this.na = ButtonBlankName[this.num]; // 임의로 버튼 이름 저장
            }
            return this.na; // 반환
        }

        public void subButton() { // 버튼에 정보를 저장함.
            b[num] = new JButton(name()); // 이름 저장 함수 선언
            b[num].addActionListener(new MyActionListener()); // 액션 감지 클래스 선언(마우스)
            c.add(b[num]); // 이렇게 정보를 저장한 버튼들은 컨테이너에 추가
        }

    }

    public class enterLayout extends JFrame { // 정보 저장 JFrame 클래스
        enterLayout(int num) { // 생성자
            String ButtonBlankName[] = {"Crtl + 1", "Crtl + 2", "Crtl + 3", "Crtl + 4", "Crtl + 5", "Crtl + 6", "Crtl + 7", "Crtl + 8", "Crtl + 9", "Crtl + 0"}; // 빈 버튼에 넣을 기본 이름들
            FileManager f = new FileManager(); // 파일 매니저 선언
            setTitle(num + "번키 입력 창"); // JFrame 창의 제목
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // X키 누를 시 이 창만 종료 되도록 dispose를 선언함.
            Container c = getContentPane(); // 컨테이너 선언
            JButton button1 = new JButton("경로 가져오는 법"); // 첫 사용자들을 위한 매뉴얼 버튼
            JButton button2 = new JButton("입력"); // 입력 후 확인 버튼
            button1.setFont(font); // 미리 저장해둔 폰트 설정
            button2.setFont(font); // 미리 저장해둔 폰트 설정
            JTextField textField_na = new JTextField(); // 이름을 입력할 공간
            JTextField textField_lo = new JTextField(); // 경로를 입력할 공간
            textField_na.setFont(font); // 미리 저장해둔 폰트 설정
            textField_lo.setFont(font); // 미리 저장해둔 폰트 설정

            GridLayout grid = new GridLayout(3, 2, 10, 5);  // 행3 열2 (3X2), 버튼간간격 옆 10 위아래 5
            Color co = new Color(205, 209, 255); // 배경색
            c.setBackground(co); // 배경색 설정
            c.setLayout(grid); // Layout 설정(grid)

            JLabel na = new JLabel("이름(버튼의 별명):");
            JLabel lo = new JLabel("프로그램(.exe) 경로:");
            na.setFont(font); // 미리 저장해둔 폰트 설정
            lo.setFont(font); // 미리 저장해둔 폰트 설정

            c.add(na); // 이름 입력 문구
            c.add(textField_na); // 버튼에 붙일 이름 값을 받음
            c.add(lo); // 경로 입력 문구
            c.add(textField_lo); // 버튼에 연결할 프로그램의 경로 값 입력
            c.add(button1); // 버튼 1 추가
            c.add(button2); // 버튼 2 추가

            button1.addActionListener(e -> { // 버튼 1을 누를 시
                new information(); // 매뉴얼 클래스 호출
            });

            button2.addActionListener(e -> { // 버튼 2를 누를 시
                f.Save(textField_na.getText(), textField_lo.getText(), num); // textfield에 입력 된 값을 파일 관리자에 저장
                if (f.bring_name(num) == null){ // 넣은 값 중 이름(name)의 값이 null(빈칸)인 경우
                    b[num - 1].setText(ButtonBlankName[num - 1]); // 버튼에 기본 이름을 입힌다.(경로만 저장 된 것)
                    dispose(); // 창을 종료 한다.
                    c.requestFocus(); // 키 값을 재 포커싱 해준다.
                }
                else if (f.bring_location(num) == null){ // 넣은 값 중 경로(location)의 값이 null(빈칸)인 경우
                    b[num - 1].setText(ButtonBlankName[num - 1]); // 값이 저장 되지 않은 것으로 간주- 버튼에 기본 이름을 입힌다.
                    dispose(); // 창을 종료 한다.
                    c.requestFocus(); // 키 값을 재 포커싱 해준다.
                }
                else{ // 이름과 경로가 모두 온전히 저장된 경우(=null 이 아닌 경우)
                    b[num - 1].setText(f.bring_name(num)); // 버튼의 이름을 사용자가 입력한 값으로 변경함.
                    dispose(); // 창을 종료 한다.
                    c.requestFocus(); // 키 값을 재 포커싱 해준다.
                }
            });

            setResizable(false); // 창의 크기를 조정하지 못하게 설정
            setSize(350, 200); // 창의 크기
            setVisible(true); // 창을 띄운다.
        }
    }

    class information extends JFrame{ // 매뉴얼 클래스
        public information() {
            setTitle("경로 가져오는 법"); // JFrame 창 제목
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // X키 누를 시 이 창만 종료 되도록 dispose를 선언함.
            Container c = getContentPane(); // 컨테이너 선언
            GridLayout grid = new GridLayout(5, 1, 0, 0);  // 행5 열1 (5X1), 버튼간간격 없음.
            c.setLayout(grid); // Layout 설정(grid)
            JLabel jl1 = new JLabel(" 1. 원하는 프로그램(.exe)을 우클릭한다.");
            JLabel jl2 = new JLabel(" 2. 속성을 누른다. ");
            JLabel jl3 = new JLabel(" 3. 프로그램의 경로를 전체선택, 복사한다. ");
            JLabel jl4 = new JLabel(" 4. 입력창의 경로 칸에 붙여넣는다.");
            JButton button = new JButton("닫기"); // 닫기 버튼

            jl1.setFont(font); // 미리 저장해둔 폰트 설정
            jl2.setFont(font);
            jl3.setFont(font);
            jl4.setFont(font);
            button.setFont(font);
            c.add(jl1); // 문구 4개 추가
            c.add(jl2);
            c.add(jl3);
            c.add(jl4);
            c.add(button); // 버튼 추가

            button.addActionListener(e ->{ // 닫기 버튼을 누른 경우
                dispose(); // 창을 종료
            });

            Color co = new Color(205, 209, 255); // 배경색
            c.setBackground(co); // 배경색 적용
            setSize(350, 280); // 창의 크기
            setVisible(true); // 창을 띄움
        }
    }

    public class deleteLayout extends JFrame { // 버튼의 이름, 경로를 삭제하는 클래스
        String[] ButtonBlankName = {"Crtl + 1", "Crtl + 2", "Crtl + 3", "Crtl + 4", "Crtl + 5", "Crtl + 6", "Crtl + 7", "Crtl + 8", "Crtl + 9", "Crtl + 0"}; // 빈 버튼에 넣을 기본 이름들
        deleteLayout(int num) { // 버튼에 값이 있는 경우
            FileManager f = new FileManager(); // 파일 관리자 선언
            Container c = getContentPane(); // 컨테이너

            setTitle("DELETE"); // 창 이름 DELETE
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // X키 누를 시 이 창만 종료 되도록 dispose를 선언함.

            c.setLayout(new FlowLayout()); //버튼 생성
            JLabel d_text = new JLabel("정말 정보를 삭제하시겠습니까?"); // 삭제 문구
            d_text.setFont(font); // 미리 저장해둔 폰트 설정
            c.add(d_text); // 삭제 문구 컨테이너에 추가

            JButton b1 = new JButton("예"); //예 버튼
            b1.setFont(font); // 미리 저장해둔 폰트 설정
            b1.addActionListener(e -> { // 예 버튼
                f.Delete(num + 1); // 파일 매니저를 호출해서 해당 파일을 삭제함.
                b[num].setText(ButtonBlankName[num]); // 파일이 삭제 됐으므로 버튼이름을 초기설정으로 바꾸어줌
                dispose(); // 창 종료
                c.requestFocus(); // 키 값을 재 포커싱 해준다.
            });
            c.add(b1); // 컨테이너에 b1버튼 추가

            JButton b2 = new JButton("아니오"); //아니오 버튼
            b2.setFont(font); // 미리 저장해둔 폰트 설정
            b2.addActionListener(e -> { // 아니오 버튼을 누르면
                dispose(); // 창 종료
                c.requestFocus(); // 키 값을 재 포커싱 해준다.
            });
            c.add(b2); // 컨테이너에 b2버튼 추가

            Color a = new Color(205, 209, 255); //배경색
            c.setBackground(a); //배경색 적용
            setSize(250, 100); //크기 사이즈
            setVisible(true); // 창을 띄운다.
            setResizable(false); // 창의 크기를 조정하지 못하게 설정
        }
        deleteLayout(){ // 버튼에 값이 없는 경우
            Container c = getContentPane(); // 컨테이너 추가

            setTitle("DELETE"); // JFrame 창 제목
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // X키 누를 시 이 창만 종료 되도록 dispose를 선언함.

            c.setLayout(new FlowLayout()); //FlowLayout으로 Layout 생성
            JLabel jl = new JLabel("가지고 있는 정보가 없습니다.");
            jl.setFont(font); // 미리 저장해둔 폰트 설정
            c.add(jl); // 문구 추가
            JButton button = new JButton("닫기"); //닫기 버튼
            button.setFont(font); // 미리 저장해둔 폰트 설정

            button.addActionListener(e -> { // 버튼을 눌렀을 경우
                dispose(); // 창을 종료한다.
                c.requestFocus(); // 키 값을 재 포커싱 해준다.
            });
            c.add(button); // 닫기 버튼 추가

            Color a = new Color(205, 209, 255); //배경색
            c.setBackground(a); //배경색 적용
            setSize(230, 100); //크기 사이즈
            setVisible(true); // 창을 띄움
            setResizable(false); // 창의 크기를 조정하지 못하게 설정
        }
    }

    class MyActionListener implements  ActionListener, KeyListener { // 액션 감지 클래스
        public void actionPerformed(ActionEvent e) { // 버튼 클릭 감지
            Object source = e.getSource(); // 마우스가 누른 버튼의 값을 얻음
            FileManager f = new FileManager(); // 파일 매니저 선언

            for (int i = 0; i < 10; i++) { // 10번반복
                if (source == b[i]) { // 숫자 (i + 1) 키 (ex) 1번키, 2번키, ..., 0번키
                    if (f.bring_name(i + 1) == null) { // (i + 1)번키에 저장된 이름 값이 null인 경우
                        new enterLayout(i + 1); // 새로운 정보를 저장하기 위해 enterLayout을 호출한다.
                        c.requestFocus(); // 키 값을 재 포커싱 해준다.
                    } else { // 저장되어 있던 이름 값이 null이 아닌 경우
                        try { // 시도함
                            new ProcessRunner(i + 1).byRuntime(); // exe파일 실행 하는 것을
                        } catch (IOException | InterruptedException w) { // 오류 발생한 경우
                            w.printStackTrace();
                        }
                        c.requestFocus(); // 키 값을 재 포커싱 해준다.
                    }
                }
            }
        }

        public void keyPressed(KeyEvent e) { // 키보드 키를 눌렀을 때 작동한다 (Crtl, Alt)
            la.setText(e.getKeyText(e.getKeyCode())); // 키보드 값을 얻음
            FileManager f = new FileManager(); // 파일 매니저 선언

            char[] Array = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}; // 1번 키, 2번 키를 배열에 선언

            if (e.getModifiers() == 2) { // Crtl키를 누른 경우
                for (int i = 0; i < Array.length; i++) { // 배열 Array의 길이만큼 반복
                    if (e.getKeyChar() == Array[i]) { // 내가 누른 키와 Array[i]의 값이 같은 경우
                        if (f.bring_name(i + 1) == null) { // (i + 1)번키에 저장된 이름 값이 null인 경우
                            new enterLayout(i + 1); // 새로운 정보를 저장하기 위해 enterLayout을 호출한다.
                            // JButton이 실행되지 않았기 때문에 키 값을 재 포커싱 해주지 않아도 상관 없다.
                        } else { // 저장되어 있던 이름 값이 null이 아닌 경우
                            try { // 시도함
                                new ProcessRunner(i + 1).byRuntime(); // exe파일 실행 하는 것을
                            } catch (IOException | InterruptedException ex) { // 에러가 발생한 경우
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            } else if (e.getModifiers() == 8) { //Alt키를 누른 경우 각 수에 대응하는 값을 제거(값이 없으면 패스)
                for (int i = 0; i < Array.length; i++) { // 배열 Array의 길이만큼 반복
                    if (e.getKeyChar() == Array[i]) { // 내가 누른 키와 Array[i]의 값이 같은 경우
                        if (f.bring_name(i + 1) != null) {  // (i + 1)번키에 저장된 이름 값이 null이 아닌 경우(값이 있음)
                            new deleteLayout(i); // 기존 값을 제거하기 위해 deleteLayout을 호출한다.
                        }
                        else{ // 저장되어 있던 이름 값이 null이 아닌 경우
                            new deleteLayout(); // 값이 없음을 알려주기 위해 호출 (사용자에게 잘못된 접근이라고 말함)
                        }
                    }
                }
            }
        }

        @Override
        public void keyTyped(KeyEvent e) { // 키를 떼는 순간
        }

        @Override
        public void keyReleased(KeyEvent e) { // 누른 키를 떼는 순간(유니코드가 입력된 경우에만)
        }
    }

    public class ProcessRunner { // 프로그램을 실행시키는 클래스
        String lo; // 경로 저장 변수
        int num; // 이름 저장 변수
        FileManager f = new FileManager(); // 파일 매니저 호출

        public ProcessRunner(int num) { // 버튼이 몇번째 키와 연결 되었는지 알 수 있음
            this.num = num;
        }

        public void byRuntime() throws IOException, InterruptedException {
            Runtime rt = Runtime.getRuntime(); // 외부 프로세스 실행 함수 선언
            this.lo = f.bring_location(num); // 파일 매니저에서 경로를 가지고 옴. (몇번째 키와 연결 되었는지 아니까)
            Process pc; // 경로를 실행할 변수

            pc = rt.exec(lo); // 외부에서 받은 주소를 대입하여 실행함.

            pc.waitFor(); // 명령어 종료시까지 대기
            pc.destroy(); // 명령어 종료시 하위 프로세스 제거
        }
    }

    public static void main(String[] args) {
        new FileManager().FileCheck(); // 파일(name.txt, location.txt)이 있는지 검사후 없을 경우 파일 생성(프로그램 실행 시 처음에만 작동함)
        new Shortcut_keys(); // Shortcut_keys_program선언
    }
}