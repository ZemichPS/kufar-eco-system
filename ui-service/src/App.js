import React, { useEffect, useState } from 'react';
import './App.css';
import LoginForm from './loginForm';
import { Button, Container } from '@mui/material';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Link, Route, Routes } from 'react-router-dom';
import JustButton from './JustButton';


function App() {
  const [users, setUsers] = useState(null);

  useEffect(() => {
    fetch('http://localhost:8012/api/v1/users', {
      headers: {
        'Authorization': 'Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJlbWFpbCI6InplbWljaGNzQGdtYWlsLmNvbSIsInN1YiI6InplbWljaGNzQGdtYWlsLmNvbSIsImlhdCI6MTc0NDk5NTk5MywiZXhwIjoxNzQ1MTM5OTkzfQ.n_9OFpdngwUEF1ahvOF7wlYASEnDg4-B60T2fQ55pEc',
      },
    })
      .then(res => res.json())
      .then(data => setUsers(data))
      .catch(err => console.error(err));
  }, []);

  if (!users) return <p>Загрузка...</p>;

  return (
    
    <Container>
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
    </Container>
  );
}

export default App;
