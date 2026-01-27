export  type Days = "monday"|"tuesday"|"wednesday"|"thursday"|"friday"|"saturday"|"sunday";

export interface Weekday {
    day: Days;
    mealId: number|null;
    mealName: string;
}
