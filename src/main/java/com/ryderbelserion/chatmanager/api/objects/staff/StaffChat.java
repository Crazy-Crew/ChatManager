package com.ryderbelserion.chatmanager.api.objects.staff;

public class StaffChat {

    private boolean isStaffChatEnabled = false;

    public void setStaffChatEnabled(final boolean isStaffChatEnabled) {
        this.isStaffChatEnabled = isStaffChatEnabled;
    }

    public boolean isStaffChatEnabled() {
        return this.isStaffChatEnabled;
    }

    private String chatFormat = "&e[&bStaffChat&e] &a{player} &7> &b{message}";

    public void setChatFormat(final String chatFormat) {
        this.chatFormat = chatFormat;
    }

    public String getChatFormat() {
        return this.chatFormat;
    }

    private boolean isBossBarEnabled = false;

    public void setBossBarEnabled(final boolean isBossBarEnabled) {
        this.isBossBarEnabled = isBossBarEnabled;
    }

    public boolean isBossBarEnabled() {
        return this.isBossBarEnabled;
    }

    private String titleFormat = "&eStaff Chat";

    public void setTitleFormat(String titleFormat) {
        this.titleFormat = titleFormat;
    }

    public String getTitleFormat() {
        return this.titleFormat;
    }
}