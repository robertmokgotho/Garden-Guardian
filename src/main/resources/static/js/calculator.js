
    // Client-side validation and dynamic preview
    const form = document.getElementById('calculatorForm');
    const calculateBtn = document.getElementById('calculateBtn');
    const savePlanBtn = document.getElementById('savePlan');
    const cropsContainer = document.getElementById('cropsContainer');
    /*<![CDATA[*/
    let cropCount = /*[[${input.crops != null and input.crops.size() > 0}]]*/false ? /*[[${input.crops.size()}]]*/1 : 1;
    /*]]>*/

    function validateForm() {
        const inputs = form.querySelectorAll('input[type="number"]');
        let valid = true;
        inputs.forEach(input => {
            if (input.value < 0 || (input.required && !input.value)) {
                valid = false;
            }
        });
        calculateBtn.disabled = !valid;
        savePlanBtn.disabled = !valid;
    }

    form.querySelectorAll('input').forEach(input => {
        input.addEventListener('input', validateForm);
    });

    // Add crop dynamically
    document.getElementById('addCrop').addEventListener('click', () => {
        cropCount++;
        const div = document.createElement('div');
        div.className = 'crop-container';
        div.innerHTML = `
            <div class="crop-header">Crop ${cropCount}</div>
            <label for="crops[${cropCount - 1}].name">Crop Name (optional):</label>
            <input type="text" name="crops[${cropCount - 1}].name" id="crops[${cropCount - 1}].name" />
            <label for="crops[${cropCount - 1}].plantSpacing">Plant Spacing:</label>
            <input type="number" name="crops[${cropCount - 1}].plantSpacing" id="crops[${cropCount - 1}].plantSpacing" step="0.01" required />
            <select name="crops[${cropCount - 1}].spacingUnit" id="crops[${cropCount - 1}].spacingUnit">
                <option value="meters">Meters</option>
                <option value="centimeters">Centimeters</option>
            </select>
            <label for="crops[${cropCount - 1}].seedsPerPack">Seeds per Pack:</label>
            <input type="number" name="crops[${cropCount - 1}].seedsPerPack" id="crops[${cropCount - 1}].seedsPerPack" required />
            <label for="crops[${cropCount - 1}].pricePerPack">Price per Seed Pack (ZAR):</label>
            <input type="number" name="crops[${cropCount - 1}].pricePerPack" id="crops[${cropCount - 1}].pricePerPack" step="0.01" required />
        `;
        cropsContainer.appendChild(div);
        div.querySelectorAll('input').forEach(input => input.addEventListener('input', validateForm));
        validateForm();
    });

    // Session storage retained as fallback
    document.getElementById('savePlan').addEventListener('click', () => {
        const formData = new FormData(form);
        const data = {};
        formData.forEach((value, key) => {
            if (key.includes('[')) {
                const [base, index, field] = key.match(/(\w+)\[(\d+)\]\.(\w+)/);
                if (!data[base]) data[base] = [];
                if (!data[base][index]) data[base][index] = {};
                data[base][index][field] = value;
            } else {
                data[key] = value;
            }
        });
        sessionStorage.setItem('gardenPlan', JSON.stringify(data));
    });

    // Draw garden layout
    const canvas = document.getElementById('gardenCanvas');
    if (canvas) {
        const ctx = canvas.getContext('2d');
        const effectiveLength = parseFloat('[[${effectiveLength}]]') || 1;
        const effectiveWidth = parseFloat('[[${effectiveWidth}]]') || 1;
        const layoutType = '[[${input.layoutType}]]' || 'grid';
        /*<![CDATA[*/
        const crops = /*[[${results != null}]]*/false ? /*[[${results}]]*/[] : [];
        /*]]>*/
        const borderWidth = parseFloat('[[${input.borderWidth}]]') || 0;
        const pathwayWidth = parseFloat('[[${input.pathwayWidth}]]') || 0;
        const pathwayLength = parseFloat('[[${input.pathwayLength}]]') || 0;

        // Scale canvas to fit garden
        const scale = Math.min(400 / effectiveLength, 400 / effectiveWidth);
        canvas.width = effectiveLength * scale;
        canvas.height = effectiveWidth * scale;
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        // Draw border
        if (borderWidth > 0) {
            ctx.strokeStyle = '#666';
            ctx.lineWidth = 2;
            ctx.strokeRect(0, 0, canvas.width, canvas.height);
        }

        // Draw pathways
        if (pathwayWidth > 0) {
            ctx.fillStyle = '#ccc';
            ctx.fillRect(0, (effectiveWidth - pathwayWidth) / 2 * scale, canvas.width, pathwayWidth * scale);
        }
        if (pathwayLength > 0) {
            ctx.fillRect((effectiveLength - pathwayLength) / 2 * scale, 0, pathwayLength * scale, canvas.height);
        }

        // Draw plants
        crops.forEach((crop, index) => {
            ctx.fillStyle = ['#ff0000', '#00ff00', '#0000ff'][index % 3]; // Different colors for crops
            const spacing = crop.plants > 0 ? Math.sqrt((effectiveLength * effectiveWidth) / crop.plants) : 1;
            const plantsAlongLength = Math.floor(effectiveLength / spacing) + 1;
            const plantsAlongWidth = Math.floor(effectiveWidth / spacing) + 1;

            for (let i = 0; i < plantsAlongLength; i++) {
                for (let j = 0; j < plantsAlongWidth; j++) {
                    let x = i * spacing * scale;
                    let y = j * spacing * scale;
                    if (layoutType === 'triangular' && j % 2 === 1) {
                        x += spacing * scale / 2; // Offset for staggered rows
                    }
                    // Avoid drawing plants on pathways
                    if (pathwayWidth > 0 && Math.abs(y - (effectiveWidth - pathwayWidth) / 2 * scale) < pathwayWidth * scale / 2) continue;
                    if (pathwayLength > 0 && Math.abs(x - (effectiveLength - pathwayLength) / 2 * scale) < pathwayLength * scale / 2) continue;
                    ctx.beginPath();
                    ctx.arc(x, y, 3, 0, 2 * Math.PI);
                    ctx.fill();
                }
            }
        });
    }

    validateForm();


