public interface ShowRepeatContract{

    interface View{
        void showView(@NonNull List<DayModel> getDayList);
    }
    
    interface Presenter{
        void setView();
    }

}