import { DateUtils } from '@/services/utilitaire/dateUtils';

export default class DatePickerCalendarDto {

    get dateCourte() {
        return this.dateUtils.formaterDateDDMM(this.date);
    }
    get dateComplete() {
        return this.dateUtils.formaterDateYYMMDD(this.date);
    }
    set dateComplete(value: string) {
        this.date = this.dateUtils.parserDateYYMMDD(value);
    }
    public datePick: boolean = false;
    public date: Date = new Date();

    private dateUtils: DateUtils = new DateUtils();
}
