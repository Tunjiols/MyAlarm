public class ShowRepeatPresenter implement ShowRepeatContract.Presenter{
    
    private ShowRepeatPresenter.View mView;
    
    public ShowRepeatPresenter(ShowRepeatPresenter.View view){
        mView = view
    }
    
    @Override
    public void setView(){
        mView.showView(getDayList);
    }

    private static List<DayModel> getDayList(){
        List<DayModel> dayList = new ArrayList<DayMoadel>();
        String[] weekName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Staturday"};
        
        for (String week : weekName){
            dayList.add(new DayModel(weekName, false));
        }
        
        return new  dayList;
    }
}