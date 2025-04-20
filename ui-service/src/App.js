import React, { useEffect, useState } from "react";
import "./App.css";
import { Button, Container } from "@mui/material";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import LoginForm from "./components/LoginForm2";

function App() {
  return (
    <div className="App">
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6">My Users</Typography>
        </Toolbar>
      </AppBar>
      <LoginForm />
    </div>
  );
}

export default App;

{
  /* <Container>
<AppBar position="static" >
  <Toolbar>
    <Typography variant="h6">
        SpareParts
    </Typography>
    <Button color="inherit" component={Link} to="/login">Login</Button>
    <Button color="inherit" component={Link} to="/about">About</Button>
  </Toolbar>
</AppBar>

<Container sx={{ mt: 2 }}>
<Routes>
  <Route path='/login' element={<LoginForm />}/>
  <Route path='/about' element={<JustButton />}/>
</Routes>
</Container>
</Container> */
}
