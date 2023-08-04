
import React from 'react';

export const fetchDataFromApi = (urlSuffix, setterFunction) => {
  React.useEffect(() => {
    // Fetch data from the backend API when the component mounts
    fetch(`http://localhost:8080/${urlSuffix}`)
      .then((response) => response.json())
      .then((data) => setterFunction(data))
      .catch((error) => console.error('Error fetching data:', error));
  }, []); // Empty dependency array to ensure it runs only once when the component mounts
};

