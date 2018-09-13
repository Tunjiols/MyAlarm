 public class DayModel{
 
        private String mDay;
        private boolean mChecked;
        
        public DayModel(String day, boolean checked){
            this.mDay = day;
            this.mChecked = checked;
        }
        
        public String getDay(){
            return mDay;
        }
        
        public void setDay(String day){
            this.mDay = day;
        }
        
        public boolean getChecked(){
            return mChecked;
        }
        
        public void setChecked(boolean checked){
            this.mChecked = checked;
        }
        
    }