-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 08-Nov-2018 às 02:28
-- Versão do servidor: 10.1.32-MariaDB
-- PHP Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bd_loja_projeto`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_clientes`
--

CREATE TABLE `tb_clientes` (
  `ID_CLIENTE` int(32) NOT NULL,
  `TXT_NOME` varchar(255) NOT NULL,
  `TXT_ENDERECO` varchar(255) NOT NULL,
  `TXT_TELEFONE` varchar(255) DEFAULT NULL,
  `TXT_EMAIL` varchar(255) DEFAULT NULL,
  `TXT_SEXO` varchar(10) DEFAULT '0',
  `TXT_RG` varchar(20) DEFAULT '0',
  `TXT_CPF` varchar(20) DEFAULT '0',
  `FLG_STATUS` char(1) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_clientes`
--

INSERT INTO `tb_clientes` (`ID_CLIENTE`, `TXT_NOME`, `TXT_ENDERECO`, `TXT_TELEFONE`, `TXT_EMAIL`, `TXT_SEXO`, `TXT_RG`, `TXT_CPF`, `FLG_STATUS`) VALUES
(1, 'CARLOS HENRIQUE DE CARVALHO AIRES', 'RUA JOÃO PEDRO DA ROCHA 55', '992325771', 'CARLOS.WECODE@GMAIL.COM', 'MASCULINO', 'MG28258741', '36578415269', 'A'),
(2, 'GABRIEL CLEBICAR ESTEVES', 'RUA XV DE NOVEMBRO 122', '992326754', 'GABRIEL_CLEBICAR@GMAIL.COM', 'MASCULINO', 'MG282543121', '36456415269', 'A'),
(3, 'DANIELA DE ALMEIDA ROCHA', 'RUA DOM PEDRO II', '988326754', 'DANI_SHOW@GMAIL.COM', 'FEMININO', 'MG2825444121', '36452354364', 'A'),
(4, 'MATHEUS ROCHA ROCHEDO', 'TRAVESSA DAS FLORES', '988387415', 'MATHEUS.ZOA@HOTMAIL', 'MASCULINO', 'SP12874632', '9874635186', 'A');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_produtos`
--

CREATE TABLE `tb_produtos` (
  `ID_PRODUTO` int(32) NOT NULL,
  `TXT_NOME_PROD` varchar(255) NOT NULL DEFAULT '0',
  `TXT_COR` varchar(255) NOT NULL DEFAULT '0',
  `TXT_TAMANHO` varchar(50) NOT NULL DEFAULT '0',
  `INT_PRECO` int(32) NOT NULL DEFAULT '0',
  `TXT_TIPO` varchar(255) NOT NULL DEFAULT '0',
  `TXT_GENERO` varchar(255) NOT NULL DEFAULT '0',
  `TXT_MARCA` varchar(255) NOT NULL DEFAULT '0',
  `QTD_ESTQ` int(200) NOT NULL DEFAULT '0',
  `QTD_MIN_ESTQ` int(20) NOT NULL DEFAULT '0',
  `FLG_STATUS` char(1) NOT NULL DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_produtos`
--

INSERT INTO `tb_produtos` (`ID_PRODUTO`, `TXT_NOME_PROD`, `TXT_COR`, `TXT_TAMANHO`, `INT_PRECO`, `TXT_TIPO`, `TXT_GENERO`, `TXT_MARCA`, `QTD_ESTQ`, `QTD_MIN_ESTQ`, `FLG_STATUS`) VALUES
(1, 'JAQUETA DE COURO', 'PRETO', 'M', 200, 'JAQUETAS', 'MASCULINO', 'ROCKERS', 50, 10, 'A'),
(2, 'SAIA MIL FLORES', 'VERMELHO', 'P', 80, 'SAIAS', 'FEMININO', 'LA VIANNAS', 100, 30, 'A'),
(3, 'CASACO DE LÃ', 'CINZA', 'M', 120, 'CASACOS', 'UNISEX', 'GUEST', 150, 50, 'A');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_usuarios`
--

CREATE TABLE `tb_usuarios` (
  `ID_USUARIO` int(32) NOT NULL,
  `TXT_NOME` varchar(255) DEFAULT NULL,
  `TXT_LOGIN` varchar(255) DEFAULT NULL,
  `TXT_TIPO` varchar(255) DEFAULT NULL,
  `TXT_SENHA` varchar(255) DEFAULT NULL,
  `TXT_STATUS` varchar(255) DEFAULT 'A'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tb_usuarios`
--

INSERT INTO `tb_usuarios` (`ID_USUARIO`, `TXT_NOME`, `TXT_LOGIN`, `TXT_TIPO`, `TXT_SENHA`, `TXT_STATUS`) VALUES
(1, 'CARLOS', 'TESTE', 'ADMINISTRADOR', 'TESTE', 'A'),
(2, 'CARLOS', 'TESTE', 'ESTOQUISTA', '1234', 'A');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tb_vendas`
--

CREATE TABLE `tb_vendas` (
  `ID_VENDA` int(32) NOT NULL,
  `INT_COD_PROD` INT(32) NOT NULL,
  `INT_QTD_VENDA` INT(32) NOT NULL,
  `INT_DESCONTO` INT(32) NOT NULL,
  `INT_TOTAL` INT(32) NOT NULL,
  `TXT_VENDEDOR` varchar(255) DEFAULT '0',
  `DAT_DATA_VENDA` varchar(255)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_clientes`
--
ALTER TABLE `tb_clientes`
  ADD PRIMARY KEY (`ID_CLIENTE`);

--
-- Indexes for table `tb_produtos`
--
ALTER TABLE `tb_produtos`
  ADD PRIMARY KEY (`ID_PRODUTO`);

--
-- Indexes for table `tb_usuarios`
--
ALTER TABLE `tb_usuarios`
  ADD PRIMARY KEY (`ID_USUARIO`);

--
-- Indexes for table `tb_vendas`
--
ALTER TABLE `tb_vendas`
  ADD PRIMARY KEY (`ID_VENDA`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_clientes`
--
ALTER TABLE `tb_clientes`
  MODIFY `ID_CLIENTE` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tb_produtos`
--
ALTER TABLE `tb_produtos`
  MODIFY `ID_PRODUTO` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_usuarios`
--
ALTER TABLE `tb_usuarios`
  MODIFY `ID_USUARIO` int(32) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `tb_vendas`
--
ALTER TABLE `tb_vendas`
  MODIFY `ID_VENDA` int(32) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
