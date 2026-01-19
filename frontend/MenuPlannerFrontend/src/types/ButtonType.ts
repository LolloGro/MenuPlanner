export interface ButtonType{
    type: 'button'|'submit'|'reset';
    text: string;
    onClick?: () => void;
    className?: string;
}
