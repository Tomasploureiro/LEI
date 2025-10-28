const filterButtons = document.querySelectorAll(".Filter input[type='button']");
const graphButtons = document.querySelectorAll(".GraphButtons input[type='button']");

filterButtons.forEach((button) => {
  button.addEventListener("click", () => {
    // Alternar classe "selected" para os botões de filtro
    if (button.classList.contains("selected")) {
      button.classList.remove("selected");
    } else if (document.querySelectorAll(".Filter .selected").length < 3) {
      button.classList.add("selected");
    }

    updateGraph();
  });
});

graphButtons.forEach((button) => {
  button.addEventListener("click", () => {
    // Garantir que apenas um botão de gráfico esteja selecionado
    graphButtons.forEach((btn) => btn.classList.remove("selected"));
    button.classList.toggle("selected");

    updateGraph();
  });
});