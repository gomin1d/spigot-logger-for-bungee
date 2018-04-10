package ua.lokha.spigotloggerforbungee.injectclasses;

import jline.Terminal;
import jline.console.ConsoleReader;
import jline.console.CursorBuffer;
import jline.console.KeyMap;
import jline.console.completer.Completer;
import jline.console.completer.CompletionHandler;
import jline.console.history.History;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;

public class InjectConsoleReader extends ConsoleReader {
    public InjectConsoleReader() throws IOException {

    }

    /**
     * so that the while does not work in from net.md_5.bungee.BungeeCordLauncher::main
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public String readLine() throws IOException {
        throw null; //null!
    }

    @Override
    public KeyMap getKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void finalize() throws Throwable {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Writer getOutput() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Terminal getTerminal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CursorBuffer getCursorBuffer() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setExpandEvents(boolean expand) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getExpandEvents() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCopyPasteDetection(boolean onoff) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCopyPasteDetectionEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setBellEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBellEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHandleUserInterrupt(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getHandleUserInterrupt() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCommentBegin(String commentBegin) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getCommentBegin() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPrompt(String prompt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPrompt() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setEchoCharacter(Character c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Character getEchoCharacter() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected String expandEvents(String str) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void back(int num) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean backspace() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean moveToEnd() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setParenBlinkTimeout(int timeout) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isKeyMap(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String accept() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int moveCursor(int num) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String readLine(Character mask) throws IOException {
        return null;
    }

    @Override
    public String readLine(String prompt) throws IOException {
        return null;
    }

    @Override
    public boolean setKeyMap(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getKeyMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String readLine(String prompt, Character mask) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addCompleter(Completer completer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeCompleter(Completer completer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<Completer> getCompleters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCompletionHandler(CompletionHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CompletionHandler getCompletionHandler() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean complete() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void printCompletionCandidates() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAutoprintThreshold(int threshold) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getAutoprintThreshold() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPaginationEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPaginationEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHistory(History history) {
        throw new UnsupportedOperationException();
    }

    @Override
    public History getHistory() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setHistoryEnabled(boolean enabled) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isHistoryEnabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean killLine() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean yank() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean yankPop() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean clearScreen() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void beep() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean paste() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addTriggeredAction(char c, ActionListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printColumns(Collection<? extends CharSequence> items) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void resetPromptLine(String prompt, String buffer, int cursorDest) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printSearchStatus(String searchTerm, String match) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printForwardSearchStatus(String searchTerm, String match) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void restoreLine(String originalPrompt, int cursorDest) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int searchBackwards(String searchTerm, int startIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int searchBackwards(String searchTerm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int searchBackwards(String searchTerm, int startIndex, boolean startsWith) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int searchForwards(String searchTerm, int startIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int searchForwards(String searchTerm) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int searchForwards(String searchTerm, int startIndex, boolean startsWith) {
        throw new UnsupportedOperationException();
    }
}
