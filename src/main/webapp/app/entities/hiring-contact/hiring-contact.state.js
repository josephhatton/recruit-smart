(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('hiring-contact', {
        parent: 'entity',
        url: '/hiring-contact?page&sort&search',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HiringContacts'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hiring-contact/hiring-contact.html',
            controller: 'HiringContactController',
            controllerAs: 'vm'
          }
        },
        params: {
          page: {
            value: '1',
            squash: true
          },
          sort: {
            value: 'id,asc',
            squash: true
          },
          search: null
        },
        resolve: {
          pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
            return {
              page: PaginationUtil.parsePage($stateParams.page),
              sort: $stateParams.sort,
              predicate: PaginationUtil.parsePredicate($stateParams.sort),
              ascending: PaginationUtil.parseAscending($stateParams.sort),
              search: $stateParams.search
            };
          }],
        }
      })
      .state('hiring-contact-detail', {
        parent: 'hiring-contact',
        url: '/hiring-contact/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'HiringContact'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/hiring-contact/hiring-contact-detail.html',
            controller: 'HiringContactDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'HiringContact', function ($stateParams, HiringContact) {
            return HiringContact.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'hiring-contact',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('hiring-contact-detail.edit', {
        parent: 'hiring-contact-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact/hiring-contact-dialog.html',
            controller: 'HiringContactDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContact', function (HiringContact) {
                return HiringContact.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact.new', {
        parent: 'hiring-contact',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact/hiring-contact-dialog.html',
            controller: 'HiringContactDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  firstName: null,
                  lastName: null,
                  phone1: null,
                  phone2: null,
                  email1: null,
                  jobTitle: null,
                  referralSource: null,
                  contactType: null,
                  email2: null,
                  middleName: null,
                  lastModifiedBy: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('hiring-contact', null, {reload: 'hiring-contact'});
          }, function () {
            $state.go('hiring-contact');
          });
        }]
      })
      .state('hiring-contact.edit', {
        parent: 'hiring-contact',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact/hiring-contact-dialog.html',
            controller: 'HiringContactDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContact', function (HiringContact) {
                return HiringContact.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hiring-contact', null, {reload: 'hiring-contact'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact-detail.job-order', {
        parent: 'hiring-contact-detail',
        url: '/job/order',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact/hiring-contact-job-order-dialog.html',
            controller: 'HiringContactJobOrderDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContact', function (HiringContact) {
                return HiringContact.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact.delete', {
        parent: 'hiring-contact',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact/hiring-contact-delete-dialog.html',
            controller: 'HiringContactDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['HiringContact', function (HiringContact) {
                return HiringContact.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('hiring-contact', null, {reload: 'hiring-contact'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('hiring-contact-detail.activity', {
        parent: 'hiring-contact-detail',
        url: '/detail/activity/{id}',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/hiring-contact/hiring-contact-activity-modal.html',
            controller: 'HiringContactActivityModalController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['HiringContact', function (HiringContact) {
                return HiringContact.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
