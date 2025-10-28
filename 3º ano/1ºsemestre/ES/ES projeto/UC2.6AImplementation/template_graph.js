let allData = []; // Variável para armazenar os dados carregados do JSON

// Função para verificar se os botões necessários estão selecionados
function isGraphReady() {
  const complexityButton = document.querySelector("#complexity");
  const authorButton = document.querySelector("#author");
  const dispersionButton = document.querySelector("#dispersion");

  return (
    complexityButton?.classList.contains("selected") &&
    authorButton?.classList.contains("selected") &&
    dispersionButton?.classList.contains("selected")
  );
}

// Função para atualizar o gráfico
function updateGraph() {
  console.log(
    "Botões selecionados:",
    [...document.querySelectorAll(".Filter .selected")].map((btn) => btn.id)
  );

  if (isGraphReady()) {
    console.log("Gráfico pronto para ser exibido.");
    loadAndDrawGraph("data.json");
  } else {
    console.log("Critérios não atendidos. Gráfico limpo.");
    document.querySelector("#graph").innerHTML = "";
  }
}

// Função para carregar dados JSON e desenhar o gráfico
function loadAndDrawGraph(jsonUrl) {
  d3.json(jsonUrl)
    .then((data) => {
      allData = data; // Armazenar os dados carregados globalmente
      console.log("Dados carregados:", allData);
      drawScatterPlot(allData); // Desenhar o gráfico inicial com todos os dados
    })
    .catch((error) => {
      console.error("Erro ao carregar o JSON:", error);
      showError("Erro ao carregar os dados. Tente novamente.");
    });
}

// Função para exibir mensagens de erro
function showError(message) {
  const errorElement = document.querySelector("#errorMessage");
  errorElement.textContent = message;
  errorElement.style.display = "block";
}

// Função para desenhar o gráfico de dispersão
function drawScatterPlot(data) {
  const width = 600, height = 400, margin = 40;

  // Limpar gráfico existente
  document.querySelector("#graph").innerHTML = "";

  // Configurar SVG
  const svg = d3
    .select("#graph")
    .append("svg")
    .attr("width", width + margin * 2)
    .attr("height", height + margin * 2)
    .append("g")
    .attr("transform", `translate(${margin}, ${margin})`);

  // Configurar escalas
  const xScale = d3
    .scaleLinear()
    .domain([0, d3.max(data, (d) => d.TotalAuthors)])
    .range([0, width]);

  const yScale = d3
    .scaleLinear()
    .domain([0, d3.max(data, (d) => d.complexity)])
    .range([height, 0]);

  // Adicionar eixos
  svg.append("g")
    .attr("transform", `translate(0, ${height})`)
    .call(d3.axisBottom(xScale).ticks(10));

  svg.append("g")
    .call(d3.axisLeft(yScale).ticks(5));

  // Adicionar pontos
  svg.selectAll("circle")
    .data(data)
    .enter()
    .append("circle")
    .attr("cx", (d) => xScale(d.TotalAuthors))
    .attr("cy", (d) => yScale(d.complexity))
    .attr("r", 5)
    .style("fill", "blue");

  // Adicionar legenda do eixo X
  svg.append("text")
    .attr("x", width / 2)
    .attr("y", height + margin / 1.5)
    .attr("text-anchor", "middle")
    .style("font-size", "14px")
    .text("Total Authors");

  // Adicionar legenda do eixo Y
  svg.append("text")
    .attr("x", -height / 2)
    .attr("y", -margin / 1.5)
    .attr("transform", "rotate(-90)")
    .attr("text-anchor", "middle")
    .style("font-size", "14px")
    .text("Cyclomatic Complexity");
}
