package interfaces;

public interface IFileActions {
    String defaultKey = "QfTjWnZq";
    IFileActions fileType();

    IFileActions archiving();

    IFileActions encryption();
    public void changeEncryptionKey(String currentKey);

}
