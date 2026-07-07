interface ErrorAlertProps {
  mensagem: string;
}

export default function ErrorAlert({ mensagem }: ErrorAlertProps) {
  return (
    <div
      style={{
        padding: "1rem",
        backgroundColor: "#f8d7da",
        color: "#721c24",
        border: "1px solid #f5c6cb",
        borderRadius: "4px",
        marginBottom: "1rem",
      }}
    >
      {mensagem}
    </div>
  );
}
