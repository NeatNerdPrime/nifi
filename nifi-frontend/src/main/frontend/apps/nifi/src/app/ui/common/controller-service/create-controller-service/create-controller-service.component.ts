/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DocumentedType } from '../../../../state/shared';
import { ExtensionCreation } from '../../extension-creation/extension-creation.component';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { CloseOnEscapeDialog } from '@nifi/shared';
import { ExtensionTypesLoadingStatus } from '../../../../state/extension-types';

@Component({
    selector: 'create-controller-service',
    imports: [ExtensionCreation, AsyncPipe],
    templateUrl: './create-controller-service.component.html',
    styleUrls: ['./create-controller-service.component.scss']
})
export class CreateControllerService extends CloseOnEscapeDialog {
    @Input() saving$!: Observable<boolean>;
    @Input() controllerServiceTypes$!: Observable<DocumentedType[]>;
    @Input() controllerServiceTypesLoadingStatus$!: Observable<ExtensionTypesLoadingStatus>;
    @Output() createControllerService: EventEmitter<DocumentedType> = new EventEmitter<DocumentedType>();

    constructor() {
        super();
    }

    controllerServiceTypeSelected(controllerServiceType: DocumentedType): void {
        this.createControllerService.next(controllerServiceType);
    }
}
