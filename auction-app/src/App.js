import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import AuctionItemList from './components/AuctionItemList';
import AuctionItemDetail from './components/AuctionItemDetail';
import AuctionItemEdit from './components/AuctionItemEdit';
import { Container } from 'react-bootstrap';
import './App.css'; // Ensure you import your CSS

const App = () => {
    const [selectedItemId, setSelectedItemId] = useState(null);

    return (
        <Router>
            <Container>
                <h1 className="my-4">Auction App</h1>
                <div className="container">
                    <div className="sidebar">
                        <AuctionItemList onSelectItem={setSelectedItemId} />
                    </div>
                    <div className="main-content">
                        <Routes>
                            <Route path="/edit/:itemId" element={<AuctionItemEdit />} />
                            <Route path="/" element={<AuctionItemDetail itemId={selectedItemId} />} />
                        </Routes>
                    </div>
                </div>
            </Container>
        </Router>
    );
};

export default App;
