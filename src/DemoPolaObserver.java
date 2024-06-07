import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DemoPolaObserver {
    public static void main(String[] args) {
        Editor editor = new Editor();
        PengelolaAcara acara = editor.getAcara();

        Pencatat pencatat = new Pencatat();
        PemberitahuEmail pemberitahuEmail = new PemberitahuEmail("hanasucianindageofani@gmail.com");

        acara.tambahkanPendengar("buka", pencatat);
        acara.tambahkanPendengar("simpan", pemberitahuEmail);

        editor.bukaBerkas("test.txt");
        editor.simpanBerkas("test.txt");
    }
}

class Editor {
    private PengelolaAcara acara = new PengelolaAcara();

    public PengelolaAcara getAcara() {
        return acara;
    }

    public void bukaBerkas(String fileName) {
        acara.notifikasi("buka", "Berkas " + fileName + " dibuka");
    }

    public void simpanBerkas(String fileName) {
        acara.notifikasi("simpan", "Berkas " + fileName + " disimpan");
    }
}

class PengelolaAcara {
    private Map<String, List<Pendengar>> listeners = new HashMap<>();

    public void tambahkanPendengar(String eventType, Pendengar listener) {
        List<Pendengar> users = listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        users.add(listener);
    }

    public void hapusPendengar(String eventType, Pendengar listener) {
        List<Pendengar> users = listeners.get(eventType);
        if (users != null) {
            users.remove(listener);
        }
    }

    public void notifikasi(String eventType, String message) {
        List<Pendengar> users = listeners.get(eventType);
        if (users != null) {
            for (Pendengar listener : users) {
                listener.update(eventType, message);
            }
        }
    }
}

interface Pendengar {
    void update(String eventType, String message);
}

class Pencatat implements Pendengar {
    @Override
    public void update(String eventType, String message) {
        System.out.println("Pencatat menerima notifikasi: " + eventType + " - " + message);
    }
}

class PemberitahuEmail implements Pendengar {
    private String email;

    public PemberitahuEmail(String email) {
        this.email = email;
    }

    @Override
    public void update(String eventType, String message) {
        System.out.println("Email ke " + email + ": Terjadi peristiwa " + eventType + " - " + message);
    }
}
