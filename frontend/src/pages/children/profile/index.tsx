import styled from "styled-components";
import background from "@/assets/img/background/background.jpg";
import ChildCard from "@/components/childCard";
import Face from "@/assets/img/smile.png";
import { useNavigate } from "react-router-dom";

const Background = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url(${background});
  background-size: cover;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const ChildrenProfilePage = () => {
  const navigate = useNavigate();

  const goMain = () => {
    navigate("/children/main");
  };

  return (
    <Background>
      <ContentContainer>
        <ChildCard
          img={Face}
          backgroundColor="#26C917"
          text="손수형"
          onClick={goMain}
        ></ChildCard>
      </ContentContainer>
    </Background>
  );
};

export default ChildrenProfilePage;
