export const DAYS = ["monday","tuesday","wednesday","thursday","friday","saturday","sunday"] as const;

export type Days = typeof DAYS[number];

export interface Weekday {
    day: Days;
    mealId: number|null;
    mealName: string;
}
