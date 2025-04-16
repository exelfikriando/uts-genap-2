import java.util.ArrayList; // Mengimpor kelas ArrayList untuk membuat daftar dinamis pasien.
import java.util.Scanner; // Mengimpor kelas Scanner untuk mendapatkan input dari pengguna.

public class HospitalQueueSystem { // Mendeklarasikan kelas utama bernama HospitalQueueSystem.
    private static ArrayList<Patient> patientQueue = new ArrayList<>(); // Membuat ArrayList statis privat bernama patientQueue untuk menyimpan objek Patient.
    private static Scanner scanner = new Scanner(System.in); // Membuat objek Scanner statis privat bernama scanner untuk membaca input dari keyboard.

    public static void main(String[] args) { // Metode utama yang menjadi titik awal eksekusi program.
        boolean running = true; // Membuat variabel boolean running untuk mengontrol loop utama program.

        System.out.println("Welcome to Hospital Queue Management System"); // Mencetak pesan selamat datang ke konsol.

        while (running) { // Loop while yang akan terus berjalan selama variabel running bernilai true.
            displayMenu(); // Memanggil metode displayMenu untuk menampilkan pilihan menu kepada pengguna.
            int choice = getValidIntInput("Enter your choice: "); // Memanggil metode getValidIntInput untuk mendapatkan pilihan menu yang valid dari pengguna.

            switch (choice) { // Pernyataan switch untuk mengeksekusi kode berdasarkan pilihan pengguna.
                case 1: // Jika pilihan adalah 1.
                    addPatient(); // Memanggil metode addPatient untuk menambahkan pasien baru ke dalam antrian.
                    break; // Keluar dari pernyataan switch.
                case 2: // Jika pilihan adalah 2.
                    serveNextPatient(); // Memanggil metode serveNextPatient untuk melayani pasien berikutnya dalam antrian.
                    break; // Keluar dari pernyataan switch.
                case 3: // Jika pilihan adalah 3.
                    displayQueue(); // Memanggil metode displayQueue untuk menampilkan antrian pasien saat ini.
                    break; // Keluar dari pernyataan switch.
                case 4: // Jika pilihan adalah 4.
                    updatePriority(); // Memanggil metode updatePriority untuk memperbarui prioritas pasien dalam antrian.
                    break; // Keluar dari pernyataan switch.
                case 5: // Jika pilihan adalah 5.
                    searchPatient(); // Memanggil metode searchPatient untuk mencari pasien berdasarkan nama.
                    break; // Keluar dari pernyataan switch.
                case 6: // Jika pilihan adalah 6.
                    System.out.println("Thank you for using Hospital Queue Management System. Goodbye!"); // Mencetak pesan terima kasih dan selamat tinggal.
                    running = false; // Mengubah nilai running menjadi false untuk menghentikan loop while.
                    break; // Keluar dari pernyataan switch.
                default: // Jika pilihan tidak sesuai dengan case di atas.
                    System.out.println("Invalid choice. Please try again."); // Mencetak pesan kesalahan untuk pilihan yang tidak valid.
            }
        }

        scanner.close(); // Menutup objek Scanner untuk melepaskan sumber daya sistem.
    }

    private static void displayMenu() { // Metode privat statis untuk menampilkan menu pilihan kepada pengguna.
        System.out.println("\n===== HOSPITAL QUEUE SYSTEM ====="); // Mencetak judul menu.
        System.out.println("1. Add a new patient to the queue"); // Mencetak opsi menu 1.
        System.out.println("2. Serve next patient"); // Mencetak opsi menu 2.
        System.out.println("3. Display current queue"); // Mencetak opsi menu 3.
        System.out.println("4. Update patient priority"); // Mencetak opsi menu 4.
        System.out.println("5. Search for a patient"); // Mencetak opsi menu 5.
        System.out.println("6. Exit"); // Mencetak opsi menu 6.
        System.out.println("================================="); // Mencetak garis penutup menu.
    }

    private static void addPatient() { // Metode privat statis untuk menambahkan pasien baru ke dalam antrian.
        String name = getValidStringInput("Enter patient name: "); // Mendapatkan nama pasien yang valid dari pengguna.
        int age = getValidIntInput("Enter patient age: "); // Mendapatkan usia pasien yang valid dari pengguna.
        String condition = getValidStringInput("Enter medical condition: "); // Mendapatkan kondisi medis pasien yang valid dari pengguna.
        int priority = getValidIntInRange("Enter priority (1-Critical to 5-Non-urgent): ", 1, 5); // Mendapatkan prioritas pasien yang valid dari pengguna dalam rentang 1-5.

        Patient newPatient = new Patient(name, age, condition, priority); // Membuat objek Patient baru dengan informasi yang didapatkan.

        int index = 0; // Menginisialisasi indeks untuk menemukan posisi yang tepat dalam antrian.
        while (index < patientQueue.size() && patientQueue.get(index).getPriority() <= priority) { // Loop untuk menemukan posisi di mana pasien baru harus dimasukkan berdasarkan prioritas.
            index++; // Increment indeks jika prioritas pasien saat ini lebih rendah atau sama dengan pasien baru.
        }
        patientQueue.add(index, newPatient); // Menambahkan pasien baru ke dalam antrian pada indeks yang ditemukan, menjaga urutan prioritas.
        System.out.println("Patient added successfully!"); // Mencetak pesan sukses setelah pasien ditambahkan.
    }

    private static void serveNextPatient() { // Metode privat statis untuk melayani pasien berikutnya dari antrian.
        if (patientQueue.isEmpty()) { // Memeriksa apakah antrian kosong.
            System.out.println("No patients in the queue."); // Mencetak pesan jika antrian kosong.
        } else { // Jika antrian tidak kosong.
            Patient next = patientQueue.remove(0); // Menghapus dan mendapatkan pasien pertama dari antrian.
            System.out.println("Serving patient: " + next.getName() + " (Priority: " + getPriorityText(next.getPriority()) + ")"); // Mencetak informasi pasien yang sedang dilayani.
        }
    }

    private static void displayQueue() { // Metode privat statis untuk menampilkan semua pasien dalam antrian.
        if (patientQueue.isEmpty()) { // Memeriksa apakah antrian kosong.
            System.out.println("The queue is empty."); // Mencetak pesan jika antrian kosong.
            return; // Keluar dari metode jika antrian kosong.
        }

        System.out.println("\nCurrent Patient Queue:"); // Mencetak judul daftar antrian.
        for (int i = 0; i < patientQueue.size(); i++) { // Loop untuk mengiterasi melalui semua pasien dalam antrian.
            Patient p = patientQueue.get(i); // Mendapatkan objek Patient pada indeks saat ini.
            System.out.println((i + 1) + ". " + p.getName() + " | Age: " + p.getAge() + // Mencetak nomor urutan, nama, usia,
                    " | Condition: " + p.getCondition() + // kondisi, dan
                    " | Priority: " + getPriorityText(p.getPriority())); // prioritas pasien.
        }
    }

    private static void updatePriority() { // Metode privat statis untuk memperbarui prioritas pasien dalam antrian.
        String name = getValidStringInput("Enter the name of the patient to update priority: "); // Mendapatkan nama pasien yang prioritasnya akan diubah.
        for (int i = 0; i < patientQueue.size(); i++) { // Loop untuk mencari pasien berdasarkan nama.
            Patient p = patientQueue.get(i); // Mendapatkan objek Patient pada indeks saat ini.
            if (p.getName().equalsIgnoreCase(name)) { // Memeriksa apakah nama pasien yang dimasukkan sesuai.
                int newPriority = getValidIntInRange("Enter new priority (1-Critical to 5-Non-urgent): ", 1, 5); // Mendapatkan prioritas baru yang valid dari pengguna.
                p.setPriority(newPriority); // Mengatur prioritas baru untuk pasien.
                // Remove and re-insert to maintain priority order
                patientQueue.remove(i); // Menghapus pasien dari posisi lamanya.
                int newIndex = 0; // Menginisialisasi indeks untuk posisi baru.
                while (newIndex < patientQueue.size() && patientQueue.get(newIndex).getPriority() <= newPriority) { // Loop untuk menemukan posisi baru berdasarkan prioritas baru.
                    newIndex++; // Increment indeks jika prioritas pasien saat ini lebih rendah atau sama dengan prioritas baru.
                }
                patientQueue.add(newIndex, p); // Menambahkan kembali pasien ke antrian pada posisi baru berdasarkan prioritasnya.
                System.out.println("Priority updated and queue reordered."); // Mencetak pesan sukses setelah prioritas diperbarui.
                return; // Keluar dari metode setelah pasien ditemukan dan diperbarui.
            }
        }
        System.out.println("Patient not found."); // Mencetak pesan jika pasien dengan nama yang dimasukkan tidak ditemukan.
    }

    private static void searchPatient() { // Metode privat statis untuk mencari pasien berdasarkan nama.
        String name = getValidStringInput("Enter patient name to search: "); // Mendapatkan nama pasien yang ingin dicari.
        for (Patient p : patientQueue) { // Loop untuk mengiterasi melalui semua pasien dalam antrian.
            if (p.getName().equalsIgnoreCase(name)) { // Memeriksa apakah nama pasien saat ini sesuai dengan yang dicari (case-insensitive).
                System.out.println("Patient found:"); // Mencetak pesan bahwa pasien ditemukan.
                System.out.println("Name: " + p.getName()); // Mencetak nama pasien.
                System.out.println("Age: " + p.getAge()); // Mencetak usia pasien.
                System.out.println("Condition: " + p.getCondition()); // Mencetak kondisi pasien.
                System.out.println("Priority: " + getPriorityText(p.getPriority())); // Mencetak prioritas pasien.
                return; // Keluar dari metode setelah pasien ditemukan.
            }
        }
        System.out.println("Patient not found in the queue."); // Mencetak pesan jika pasien tidak ditemukan dalam antrian.
    }

    private static String getPriorityText(int priority) { // Metode privat statis untuk mendapatkan representasi teks dari nilai prioritas.
        switch (priority) { // Pernyataan switch berdasarkan nilai prioritas.
            case 1: // Jika prioritas adalah 1.
                return "1-Critical"; // Mengembalikan teks "1-Critical".
            case 2: // Jika prioritas adalah 2.
                return "2-Urgent"; // Mengembalikan teks "2-Urgent".
            case 3: // Jika prioritas adalah 3.
                return "3-High"; // Mengembalikan teks "3-High".
            case 4: // Jika prioritas adalah 4.
                return "4-Medium"; // Mengembalikan teks "4-Medium".
            case 5: // Jika prioritas adalah 5.
                return "5-Non-urgent"; // Mengembalikan teks "5-Non-urgent".
            default: // Jika nilai prioritas tidak valid.
                return "Unknown"; // Mengembalikan teks "Unknown".
        }
    }

    private static int getValidIntInput(String prompt) { // Metode privat statis untuk mendapatkan input integer yang valid dari pengguna.
        int value; // Mendeklarasikan variabel untuk menyimpan nilai input.
        while (true) { // Loop tak terbatas yang akan terus berjalan hingga input yang valid diterima.
            System.out.print(prompt); // Mencetak prompt ke konsol untuk meminta input.
            try { // Blok try untuk mencoba mengonversi input menjadi integer.
                value = Integer.parseInt(scanner.nextLine().trim()); // Membaca input dari pengguna, menghapus spasi di awal dan akhir, dan mengonversinya menjadi integer.
                break; // Keluar dari loop jika konversi berhasil.
            } catch (NumberFormatException e) { // Blok catch untuk menangani jika input bukan angka.
                System.out.println("Invalid input. Please enter a number."); // Mencetak pesan kesalahan.
            }
        }
        return value; // Mengembalikan nilai integer yang valid.
    }

    private static int getValidIntInRange(String prompt, int min, int max) { // Metode privat statis untuk mendapatkan input integer yang valid dalam rentang tertentu dari pengguna.
        int value; // Mendeklarasikan variabel untuk menyimpan nilai input.
        while (true) { // Loop tak terbatas hingga input yang valid dalam rentang diterima.
            value = getValidIntInput(prompt); // Mendapatkan input integer yang valid menggunakan metode getValidIntInput.
            if (value >= min && value <= max) { // Memeriksa apakah nilai input berada dalam rentang yang ditentukan.
                break; // Keluar dari loop jika nilai berada dalam rentang.
            }
            System.out.println("Please enter a value between " + min + " and " + max + "."); // Mencetak pesan kesalahan jika nilai di luar rentang.
        }
        return value; // Mengembalikan nilai integer yang valid dalam rentang.
    }

    private static String getValidStringInput(String prompt) { // Metode privat statis untuk mendapatkan input string yang tidak kosong dari pengguna.
        String value; // Mendeklarasikan variabel untuk menyimpan nilai input.
        while (true) { // Loop tak terbatas hingga input string yang tidak kosong diterima.
            System.out.print(prompt); // Mencetak prompt ke konsol.
            value = scanner.nextLine().trim(); // Membaca input dari pengguna dan menghapus spasi di awal dan akhir.
            if (!value.isEmpty()) { // Memeriksa apakah string input tidak kosong.
                break; // Keluar dari loop jika string tidak kosong.
            }
            System.out.println("Input cannot be empty. Please try again."); // Mencetak pesan kesalahan jika input kosong.
        }
        return value; // Mengembalikan string input yang valid.
    }
}

class Patient { // Mendeklarasikan kelas Patient untuk merepresentasikan seorang pasien.
    private String name; // Variabel privat untuk menyimpan nama pasien.
    private int age; // Variabel privat untuk menyimpan usia pasien.
    private String condition; // Variabel privat untuk menyimpan kondisi medis pasien.
    private int priority; // Variabel privat untuk menyimpan prioritas pasien (1-Critical hingga 5-Non-urgent).


    public Patient(String name, int age, String condition, int priority) { // Konstruktor untuk membuat objek Patient baru.
        this.name = name; // Menginisialisasi nama pasien.
        this.age = age; // Menginisialisasi usia pasien.
        this.condition = condition; // Menginisialisasi kondisi medis pasien.
        this.priority = priority; // Menginisialisasi prioritas pasien.
    }

    public String getName() { // Metode publik untuk mendapatkan nama pasien.
        return name; // Mengembalikan nilai dari variabel name.
    }

    public int getAge() { // Metode publik untuk mendapatkan usia pasien.
        return age; // Mengembalikan nilai dari variabel age.
    }

    public String getCondition() { // Metode publik untuk mendapatkan kondisi medis pasien.
        return condition; // Mengembalikan nilai dari variabel condition.
    }

    public int getPriority() { // Metode publik untuk mendapatkan prioritas pasien.
        return priority; // Mengembalikan nilai dari variabel priority.
    }

    public void setPriority(int priority) { // Metode publik untuk mengatur prioritas pasien.
        this.priority = priority; // Mengubah nilai dari variabel priority.
    }
}
